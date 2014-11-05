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
package pt.ist.fenixedu.delegates.service;

import static org.fenixedu.academic.predicate.AccessControl.check;

import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.student.Student;
import org.fenixedu.academic.domain.util.email.ConcreteReplyTo;
import org.fenixedu.academic.domain.util.email.Message;
import org.fenixedu.academic.domain.util.email.Recipient;
import org.fenixedu.academic.predicate.RolePredicates;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;
import org.fenixedu.academic.util.Bundle;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.groups.UserGroup;
import org.fenixedu.bennu.core.i18n.BundleUtil;

import pt.ist.fenixedu.delegates.domain.elections.DelegateElectionBlankVote;
import pt.ist.fenixedu.delegates.domain.elections.DelegateElectionVote;
import pt.ist.fenixedu.delegates.domain.elections.DelegateElectionVotingPeriod;
import pt.ist.fenixedu.delegates.domain.elections.YearDelegateElection;
import pt.ist.fenixframework.Atomic;

public class VoteYearDelegateElections {

    @Atomic
    public static void run(YearDelegateElection yearDelegateElection, Student student, Student votedStudent)
            throws FenixServiceException {
        check(RolePredicates.STUDENT_PREDICATE);

        DelegateElectionVotingPeriod votingPeriod = yearDelegateElection.getCurrentVotingPeriod();

        try {
            if (!votingPeriod.getVotingStudentsSet().contains(student)) {
                final String fromName = getString("VoteYearDelegateElections.email.fromName");
                final String fromAddress = getString("VoteYearDelegateElections.email.fromAddress");
                final String subject = fromName + "-" + getString("VoteYearDelegateElections.email.subject");
                final String msg = getString("VoteYearDelegateElections.email.message");
                final Person person = student.getPerson();
                DelegateElectionVote vote = createDelegateElectionVote(votingPeriod, votedStudent);
                votingPeriod.addVotingStudents(student);
                votingPeriod.addVotes(vote);
                new Message(Bennu.getInstance().getSystemSender(), new ConcreteReplyTo(fromAddress).asCollection(),
                        new Recipient(UserGroup.of(person.getUser())).asCollection(), subject, msg, "");
            } else {
                throw new FenixServiceException("error.student.elections.voting.studentAlreadyVoted");
            }
        } catch (DomainException ex) {
            throw new FenixServiceException(ex.getMessage(), ex.getArgs());
        }
    }

    private static String getString(final String key) {
        return BundleUtil.getString(Bundle.DELEGATE, key);
    }

    private static DelegateElectionVote createDelegateElectionVote(DelegateElectionVotingPeriod votingPeriod, Student votedStudent) {
        if (DelegateElectionBlankVote.isBlankVote(votedStudent)) {
            return new DelegateElectionBlankVote(votingPeriod);
        }
        return new DelegateElectionVote(votingPeriod, votedStudent);
    }

}