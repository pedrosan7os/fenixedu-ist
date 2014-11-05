/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Core.
 *
 * FenixEdu Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.delegates.ui.struts.action.pedagogicalCouncil.delegates;

import org.fenixedu.academic.ui.struts.action.pedagogicalCouncil.PedagogicalCouncilApp.PedagogicalDelegatesApp;
import org.fenixedu.bennu.struts.annotations.Forward;
import org.fenixedu.bennu.struts.annotations.Forwards;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.portal.StrutsFunctionality;

import pt.ist.fenixedu.delegates.ui.struts.action.commons.delegates.FindDelegatesDispatchAction;

@StrutsFunctionality(app = PedagogicalDelegatesApp.class, path = "find", titleKey = "link.findDelegates")
@Mapping(module = "pedagogicalCouncil", path = "/findDelegates")
@Forwards({ @Forward(name = "showDelegates", path = "/commons/delegates/showDelegates.jsp"),
        @Forward(name = "searchDelegates", path = "/commons/delegates/searchDelegates.jsp") })
public class FindDelegatesDispatchActionForPedagogicalCouncil extends FindDelegatesDispatchAction {
}