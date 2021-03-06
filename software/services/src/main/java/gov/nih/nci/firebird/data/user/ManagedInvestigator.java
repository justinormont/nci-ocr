/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The NCI OCR
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This NCI OCR Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the NCI OCR Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the NCI OCR Software; (ii) distribute and
 * have distributed to and by third parties the NCI OCR Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.firebird.data.user;

import static gov.nih.nci.firebird.data.user.ManagedInvestigatorStatus.APPROVED;
import static gov.nih.nci.firebird.data.user.ManagedInvestigatorStatus.SUSPENDED;
import static gov.nih.nci.firebird.data.user.ManagedInvestigatorStatus.SUSPENDED_FROM_PROFILE;
import static gov.nih.nci.firebird.data.user.ManagedInvestigatorStatus.SUSPENDED_FROM_REGISTRATIONS;
import gov.nih.nci.firebird.data.InvestigatorProfile;

import java.util.Date;
import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Links a registration coordinator user to their managed investigators.
 */
@Entity
@Table(name = "managed_investigator", uniqueConstraints = { @UniqueConstraint(columnNames = {
        "firebird_user_id", "investigator_id" }) })
public class ManagedInvestigator implements PersistentObject {

    /**
     * Set of states that are considered in suspension.
     */
    public static final EnumSet<ManagedInvestigatorStatus> SUSPENSION_STATES = EnumSet.of(SUSPENDED,
            SUSPENDED_FROM_PROFILE, SUSPENDED_FROM_REGISTRATIONS);
    /**
     * Set of states that are partial suspension.
     */
    public static final EnumSet<ManagedInvestigatorStatus> PARTIAL_SUSPENSION_STATES = EnumSet.of(
            SUSPENDED_FROM_PROFILE, SUSPENDED_FROM_REGISTRATIONS);
    /**
     * Set of states that are considered approved.
     */
    public static final EnumSet<ManagedInvestigatorStatus> APPROVED_STATES = EnumSet.of(SUSPENDED_FROM_PROFILE,
            SUSPENDED_FROM_REGISTRATIONS, SUSPENDED, APPROVED);

    private static final long serialVersionUID = 1L;
    private Long id;
    private FirebirdUser user;
    private InvestigatorProfile investigatorProfile;
    private Date createDate;
    private ManagedInvestigatorStatus status;
    private Date statusDate;
    private boolean ctepAssociate;

    @SuppressWarnings("unused")
    // no-arg constructor required by Hibernate
    private ManagedInvestigator() {
        super();
    }

    /**
     * Creates a new role.
     *
     * @param user the user the role belongs to
     * @param investigatorProfile the investigator being managed.
     */
    ManagedInvestigator(FirebirdUser user, InvestigatorProfile investigatorProfile) {
        setUser(user);
        setInvestigatorProfile(investigatorProfile);
        setCreateDate(new Date());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    @SuppressWarnings("ucd")
    // setter required by hibernate, used in tests
    void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the Investigator's Profile
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "investigator_id")
    @ForeignKey(name = "investigator_fkey")
    @NotNull
    public InvestigatorProfile getInvestigatorProfile() {
        return investigatorProfile;
    }

    private void setInvestigatorProfile(InvestigatorProfile investigatorProfile) {
        this.investigatorProfile = investigatorProfile;
    }

    /**
     * @return the user
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "firebird_user_id")
    @ForeignKey(name = "registration_coordinator_role_user_fkey")
    public FirebirdUser getUser() {
        return user;
    }

    private void setUser(FirebirdUser user) {
        this.user = user;
    }

    /**
     * @return the current status
     */
    @Enumerated(EnumType.STRING)
    public ManagedInvestigatorStatus getStatus() {
        return status;
    }

    /**
     * @param status status
     */
    public void setStatus(ManagedInvestigatorStatus status) {
        setStatusDate(new Date());
        this.status = status;
    }

    /**
     * @return the last status update date.
     */
    @NotNull
    @Column(name = "status_date")
    public Date getStatusDate() {
        return statusDate;
    }

    private void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return true if the user is a CTEP associate
     */
    @NotNull
    @Column(name = "ctep_associate")
    public boolean isCtepAssociate() {
        return ctepAssociate;
    }

    /**
     * @param ctepAssociate indicates if the user is a CTEP associate
     */
    public void setCtepAssociate(boolean ctepAssociate) {
        this.ctepAssociate = ctepAssociate;
    }

    /**
     * @return true if the coordinator's request was approved by the investigator.
     */
    @Transient
    public boolean isApproved() {
        return isCtepAssociate() || APPROVED_STATES.contains(status);
    }

    /**
     * @return the date the coordinator requested the role.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    private void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return true if the coordinator has been suspended from managing the investigator's protocol registrations
     */
    @Transient
    public boolean isSuspendedRegistrationAccess() {
        return status == SUSPENDED || status == SUSPENDED_FROM_REGISTRATIONS;
    }

    /**
     * @return true if the coordinator has been suspended from managing the investigator's profile
     */
    @Transient
    public boolean isSuspendedProfileAccess() {
        return status == SUSPENDED || status == SUSPENDED_FROM_PROFILE;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ManagedInvestigator)) {
            return false;
        }
        ManagedInvestigator other = (ManagedInvestigator) o;
        return new EqualsBuilder().append(getUser(), other.getUser())
                .append(getInvestigatorProfile(), other.getInvestigatorProfile()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getUser()).append(getInvestigatorProfile()).toHashCode();
    }

}
