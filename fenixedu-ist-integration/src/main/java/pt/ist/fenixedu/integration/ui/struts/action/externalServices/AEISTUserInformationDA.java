/**
 * Copyright © 2013 Instituto Superior Técnico
 *
 * This file is part of FenixEdu IST Integration.
 *
 * FenixEdu IST Integration is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu IST Integration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu IST Integration.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.integration.ui.struts.action.externalServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.domain.student.StudentDataShareAuthorization;
import org.fenixedu.academic.domain.student.StudentDataShareStudentsAssociationAuthorization;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.json.simple.JSONObject;

import pt.ist.fenixedu.integration.FenixEduIstIntegrationConfiguration;

import com.google.common.base.Strings;

@Mapping(module = "external", path = "/userForAEIST")
public class AEISTUserInformationDA extends ExternalInterfaceDispatchAction {

    private static final String USER_NOT_FOUND_CODE = "USER_NOT_FOUND";
    private static final String USER_DOES_NOT_ALLOW = "USER_DOES_NOT_ALLOW";
    private static final String NOT_ACTIVE_STUDENT = "NOT_ACTIVE_STUDENT";

    private boolean doLogin(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        final String username = (String) getFromRequest(request, "username");
        final String password = (String) getFromRequest(request, "password");
        final String usernameProp = FenixEduIstIntegrationConfiguration.getConfiguration().getExternalServicesAEISTUsername();
        final String passwordProp = FenixEduIstIntegrationConfiguration.getConfiguration().getExternalServicesAEISTPassword();
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(usernameProp)
                || Strings.isNullOrEmpty(passwordProp)) {
            return false;
        }
        return username.equals(usernameProp) && password.equals(passwordProp);
    }

    @SuppressWarnings("unchecked")
    public ActionForward getBasicUserData(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        final JSONObject payload = new JSONObject();
        if (doLogin(mapping, actionForm, request, response)) {
            final String istID = (String) getFromRequest(request, "istID");
            final Person person = Person.readPersonByUsername(istID);

            if (person != null) {
                Registration registration = person.getStudent().getLastActiveRegistration();
                if (registration != null) {
                    boolean allowAccess = false;
                    StudentDataShareStudentsAssociationAuthorization dataStudentsAssociationAuthorization =
                            registration.getStudent().getStudentPersonalDataStudentsAssociationAuthorization();
                    if (dataStudentsAssociationAuthorization != null
                            && dataStudentsAssociationAuthorization.getAuthorizationChoice().isForStudentsAssociation()) {
                        allowAccess = true;
                    } else {
                        StudentDataShareAuthorization dataAuthorizationStudentsAssociation =
                                registration.getStudent().getActivePersonalDataAuthorization();
                        allowAccess =
                                dataAuthorizationStudentsAssociation != null
                                        && dataAuthorizationStudentsAssociation.getAuthorizationChoice()
                                                .isForStudentsAssociation();
                    }
                    if (allowAccess) {
                        final JSONObject jsonObject = new JSONObject();
                        jsonObject.put("email", person.getEmailForSendingEmails());
                        jsonObject.put("degree", registration.getLastDegreeCurricularPlan().getDegree().getSigla());
                        jsonObject.put("name", person.getName());
                        jsonObject.put("birthdate", person.getDateOfBirthYearMonthDay().toString());
                        jsonObject.put("address", person.getAddress());
                        jsonObject.put("locality", person.getParishOfResidence());
                        jsonObject.put("zipCode", person.getPostalCode());
                        jsonObject.put("cellphone", person.getDefaultMobilePhoneNumber());
                        jsonObject.put("phone", person.getDefaultPhoneNumber());
                        jsonObject.put("nif", person.getSocialSecurityNumber());
                        jsonObject.put("citizenCard/BI", person.getDocumentIdNumber());
                        payload.put("status", SUCCESS_CODE);
                        payload.put("data", jsonObject);
                    } else {
                        payload.put("status", USER_DOES_NOT_ALLOW);
                    }
                } else {
                    payload.put("status", NOT_ACTIVE_STUDENT);
                }
            } else {
                payload.put("status", USER_NOT_FOUND_CODE);
            }
        } else {
            payload.put("status", NOT_AUTHORIZED_CODE);
        }
        writeJSONObject(response, payload);
        return null;
    }

}
