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
package pt.ist.fenixedu.delegates.dto;

import java.io.Serializable;

import org.fenixedu.academic.domain.CurricularYear;
import org.fenixedu.academic.domain.Degree;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.joda.time.YearMonthDay;

import pt.ist.fenixedu.delegates.domain.elections.DelegateElection;
import pt.ist.fenixedu.delegates.domain.elections.DelegateElectionVotingPeriod;

public class ElectionPeriodBean implements Serializable {
    private DegreeType degreeType;

    private ExecutionYear executionYear;

    private Degree degree;

    private CurricularYear curricularYear;

    private YearMonthDay startDate;

    private YearMonthDay endDate;

    private DelegateElection election;

    private DelegateElectionVotingPeriod delegateElectionVotingPeriod;

    private boolean removeCandidacyPeriod;

    public ElectionPeriodBean() {
        setCurricularYear(null);
        setDegree(null);
    }

    public CurricularYear getCurricularYear() {
        return (curricularYear);
    }

    public void setCurricularYear(CurricularYear curricularYear) {
        this.curricularYear = curricularYear;
    }

    public Degree getDegree() {
        return (degree);
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    public YearMonthDay getEndDate() {
        return endDate;
    }

    public void setEndDate(YearMonthDay endDate) {
        this.endDate = endDate;
    }

    public YearMonthDay getStartDate() {
        return startDate;
    }

    public void setStartDate(YearMonthDay startDate) {
        this.startDate = startDate;
    }

    public DelegateElection getElection() {
        return (election);
    }

    public void setElection(DelegateElection election) {
        this.election = election;
    }

    public boolean getRemoveCandidacyPeriod() {
        return removeCandidacyPeriod;
    }

    public void setRemoveCandidacyPeriod(boolean removeCandidacyPeriod) {
        this.removeCandidacyPeriod = removeCandidacyPeriod;
    }

    public ExecutionYear getExecutionYear() {
        return (executionYear);
    }

    public void setExecutionYear(ExecutionYear executionYear) {
        this.executionYear = executionYear;
    }

    public void setDelegateElectionVotingPeriod(DelegateElectionVotingPeriod delegateElectionVotingPeriod) {
        this.delegateElectionVotingPeriod = delegateElectionVotingPeriod;
    }

    public DelegateElectionVotingPeriod getDelegateElectionVotingPeriod() {
        return delegateElectionVotingPeriod;
    }
}
