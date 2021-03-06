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
package gov.nih.nci.firebird.web.action.investigator.registration;

import gov.nih.nci.firebird.data.Certificate;
import gov.nih.nci.firebird.data.CertificateType;
import gov.nih.nci.firebird.data.FirebirdFile;
import gov.nih.nci.firebird.data.FormTypeEnum;
import gov.nih.nci.firebird.data.HumanResearchCertificateForm;
import gov.nih.nci.firebird.data.TrainingCertificate;
import gov.nih.nci.firebird.service.investigatorprofile.InvestigatorProfileService;
import gov.nih.nci.firebird.service.registration.ProtocolRegistrationService;

import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Human Research Certificate tab action.
 */
@Namespace("/investigator/registration/ajax/humanresearchcertificate")
@InterceptorRef("registrationManagementStack")
@Result(location = "human_research_certificate_tab.jsp")
public class HumanResearchCertificateTabAction extends AbstractRegistrationTabAction {

    private static final long serialVersionUID = 1L;

    private TrainingCertificate selectedCertificate = new TrainingCertificate();

    /**
     * Creates an action instance.
     *
     * @param registrationService registration service
     * @param profileService profile service
     * @param resources FIREBIRD resource bundle
     */
    @Inject
    public HumanResearchCertificateTabAction(ProtocolRegistrationService registrationService,
            InvestigatorProfileService profileService,
            ResourceBundle resources) {
        super(registrationService, profileService, resources);
    }

    @Override
    public void prepare() {
        super.prepare();
        findCertificateInProfile();
    }

    private void findCertificateInProfile() {
        for (TrainingCertificate certificate : getProfile().getCredentials(TrainingCertificate.class)) {
            if (certificate.getId().equals(selectedCertificate.getId())
                    && certificate.getCertificateType() == CertificateType.HUMAN_RESEARCH_CERTIFICATE) {
                setSelectedCertificate(certificate);
                return;
            }
        }
        setSelectedCertificate(null);
    }

    /**
     * Enter the Human Research Certificate tab.
     *
     * @return SUCCESS
     */
    @Action("enter")
    public String enterHumanResearchCertificateTab() {
        addValidationFailuresIfNecessary();
        return SUCCESS;
    }

    /**
     * add a certificate to the Selected Certificates set in the human research certificate form.
     *
     * @return SUCCESS
     */
    @Action("selectCertificate")
    public String selectCertificate() {
        verifyUnlocked();
        if (getSelectedCertificate() != null) {
            getForm().selectCertificate(selectedCertificate);
            saveRegistration(FormTypeEnum.HUMAN_RESEARCH_CERTIFICATE);
        }
        addValidationFailuresIfNecessary();
        return ACTION_ERRORS;
    }

    private void verifyUnlocked() {
        if (getRegistration().isLockedForInvestigator()) {
            throw new IllegalStateException("Registration is currently locked and unmodifiable!");
        }
    }

    /**
     * delete a document from the financial disclosure.
     *
     * @return SUCCESS
     */
    @Action("deselectCertificate")
    public String deselectCertificate() {
        verifyUnlocked();
        if (getSelectedCertificate() != null) {
            getForm().deselectCertificate(selectedCertificate);
            saveRegistration(FormTypeEnum.HUMAN_RESEARCH_CERTIFICATE);
        }
        addValidationFailuresIfNecessary();
        return ACTION_ERRORS;
    }

    /**
     * @return the JSON result of the Set of documents.
     * @throws JSONException a problem occurred when serializing.
     */
    public String getCertificatesAsJson() throws JSONException {
        Collection<Pattern> excludes = Lists.newArrayList(Pattern.compile(".*\\.byteDataSource"));
        Set<CertificateListing> certificates = findPHRCertificates();
        return JSONUtil.serialize(certificates, excludes, null, false, true, false);
    }

    private Set<CertificateListing> findPHRCertificates() {
        Set<CertificateListing> certificateListings = Sets.newHashSet();
        Set<? extends Certificate> certificates = getCertificateList();

        for (Certificate certificate : certificates) {
            if (CertificateType.HUMAN_RESEARCH_CERTIFICATE == certificate.getCertificateType()
                    && !certificate.isExpired()) {
                certificateListings.add(new CertificateListing(certificate));
            }
        }
        return certificateListings;
    }

    private Set<? extends Certificate> getCertificateList() {
        if (getRegistration().isLockedForInvestigator()) {
            return getForm().getCertificates();
        } else {
            return Sets.newHashSet(getProfile().getCredentials(TrainingCertificate.class));
        }
    }

    /**
     * @return the selectedCertificate
     */
    public TrainingCertificate getSelectedCertificate() {
        return selectedCertificate;
    }

    /**
     * @param selectedCertificate the selectedCertificate to set
     */
    public void setSelectedCertificate(TrainingCertificate selectedCertificate) {
        this.selectedCertificate = selectedCertificate;
    }

    @Override
    public HumanResearchCertificateForm getForm() {
        return getRegistration().getHumanResearchCertificateForm();
    }

    /**
     * Class for portraying Certificates in a table with just necessary data.
     */
    @SuppressWarnings("ucd")
    // needs to be protected for JSONUtil.serialize()
    protected final class CertificateListing {
        private final Long id;
        private final FirebirdFile file;
        private final Date effectiveDate;
        private final Date expirationDate;
        private final String certificateType;
        private final String issuerName;

        CertificateListing(Certificate certificate) {
            this.id = certificate.getId();
            this.file = certificate.getFile();
            this.certificateType = getText(certificate.getCertificateType().getNameProperty());
            this.effectiveDate = certificate.getEffectiveDate();
            this.expirationDate = certificate.getExpirationDate();
            this.issuerName = certificate.getIssuer().getName();
        }

        /**
         * @return .
         */
        public long getId() {
            return id;
        }

        /**
         * @return .
         */
        public FirebirdFile getFile() {
            return file;
        }

        /**
         * @return .
         */
        public Date getEffectiveDate() {
            return effectiveDate;
        }

        /**
         * @return .
         */
        public Date getExpirationDate() {
            return expirationDate;
        }

        /**
         * @return .
         */
        public String getCertificateType() {
            return certificateType;
        }

        /**
         *
         * @return Name of Organization issuing the certificate
         */
        public String getIssuerName() {
            return issuerName;
        }
    }

}
