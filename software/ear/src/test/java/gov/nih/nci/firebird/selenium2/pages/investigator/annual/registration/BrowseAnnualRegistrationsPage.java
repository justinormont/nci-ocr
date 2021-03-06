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
package gov.nih.nci.firebird.selenium2.pages.investigator.annual.registration;

import com.google.common.collect.Lists;

import gov.nih.nci.firebird.commons.selenium2.util.JQueryUtils;
import gov.nih.nci.firebird.commons.selenium2.util.WebElementUtils;
import gov.nih.nci.firebird.data.AnnualRegistration;
import gov.nih.nci.firebird.data.AnnualRegistrationType;
import gov.nih.nci.firebird.data.FormOptionality;
import gov.nih.nci.firebird.data.FormStatus;
import gov.nih.nci.firebird.data.RegistrationStatus;
import gov.nih.nci.firebird.selenium2.pages.base.AbstractMenuPage;
import gov.nih.nci.firebird.selenium2.pages.base.TableListing;
import gov.nih.nci.firebird.selenium2.pages.util.FirebirdTableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static gov.nih.nci.firebird.commons.selenium2.util.TableUtils.getCells;
import static org.junit.Assert.*;

/**
 * investigator/annual/registration/browse_annual_registrations.jsp
 */
public class BrowseAnnualRegistrationsPage extends AbstractMenuPage<BrowseAnnualRegistrationsPage> {
    private static final String REGISTRATION_TABLE_EXPAND_BUTTON_SELECTOR = ".accordionToggle";
    private static final String TABLE_ID = "annualRegistrationsAccordionTable";
    private static final String REGISTRATION_ROWS_SELECTOR = ".accordionHeader";
    private static final String FORMS_TABLE_ID_PREFIX = "annualRegistrationsTable_";
    public static final String WITHDRAW_SUBMISSION_BUTTON_ID = "withdrawSubmission";
    public static final String REACTIVATE_BUTTON_ID = "reactivateButton";
    public static final String CREATE_REGISTRATION_BUTTON_ID = "createAnnualRegistration";

    @FindBy(id = WITHDRAW_SUBMISSION_BUTTON_ID)
    private WebElement withdrawSubmissionButton;
    @FindBy(id = REACTIVATE_BUTTON_ID)
    private WebElement reactivateButton;
    @FindBy(id = CREATE_REGISTRATION_BUTTON_ID)
    private WebElement createRegistrationButton;

    private BrowseAnnualRegistrationsPageHelper helper;

    public BrowseAnnualRegistrationsPage(WebDriver driver) {
        super(driver);
        helper = new BrowseAnnualRegistrationsPageHelper(this);
    }

    public BrowseAnnualRegistrationsPageHelper getHelper() {
        return helper;
    }

    public List<RegistrationListing> getRegistrationListings() {
        List<RegistrationListing> listings = Lists.newArrayList();
        List<WebElement> rows = findElements(By.cssSelector(REGISTRATION_ROWS_SELECTOR));
        for (WebElement row : rows) {
            listings.add(new RegistrationListing(row));
        }
        return listings;
    }

    public List<FormListing> getFormListings(AnnualRegistration registration) {
        return FirebirdTableUtils.transformDataTableRows(this, getRegistrationFormsTable(registration),
                FormListing.class);
    }

    private WebElement getRegistrationFormsTable(AnnualRegistration registration) {
        return findElement(By.id(FORMS_TABLE_ID_PREFIX + registration.getId()));
    }

    public boolean hasWithdrawSubmissionButton() {
        return isPresent(By.id(WITHDRAW_SUBMISSION_BUTTON_ID));
    }

    public WithdrawSubmissionDialog clickWithdrawSubmission() {
        withdrawSubmissionButton.click();
        return new WithdrawSubmissionDialog(getDriver(), this).waitUntilReady();
    }

    public boolean hasReactivateButton() {
        return isPresent(By.id(REACTIVATE_BUTTON_ID));
    }

    public void clickReactivate() {
        reactivateButton.click();
        pause(500); // wait for page to reload and reactivate button to be removed
        waitUntilReady();
    }

    public boolean isCreateRegistrationButtonPresent() {
        return isPresent(By.id(CREATE_REGISTRATION_BUTTON_ID));
    }

    public void clickCreateRegistration() {
        createRegistrationButton.click();
        waitUntilReady();
    }

    @Override
    protected void assertLoaded() {
        super.assertLoaded();
        assertFalse(JQueryUtils.isAjaxCallExecuting(getDriver()));
        assertFalse(JQueryUtils.isLoadingIconDisplayed(getDriver()));
        assertElementWithIdPresent(TABLE_ID);
        openAllRegistrationTables();
    }

    private void openAllRegistrationTables() {
        List<WebElement> tableExpandButtons = findElements(By.cssSelector(REGISTRATION_TABLE_EXPAND_BUTTON_SELECTOR));
        for (WebElement tableExpandButton : tableExpandButtons) {
            tableExpandButton.click();
        }
    }

    public class RegistrationListing implements TableListing {
        private final static int TYPE_COLUMN = 0;
        private final static int STATUS_COLUMN = 1;
        private final static int SUBMISSION_DATE_COLUMN = 2;
        private final static int DUE_DATE_COLUMN = 3;

        private final Long id;
        private final AnnualRegistrationType type;
        private final RegistrationStatus status;
        private final String submissionDate;
        private final String dueDate;
        private final WebElement editButton;

        public RegistrationListing(WebElement row) {
            id = Long.valueOf(WebElementUtils.getId(row));
            List<WebElement> cells = row.findElements(By.tagName("div"));
            type = AnnualRegistrationType.getByDisplay(cells.get(TYPE_COLUMN).getText());
            WebElement statusSpan = cells.get(STATUS_COLUMN).findElement(By.id("registrationStatus"));
            status = RegistrationStatus.getByDisplay(statusSpan.getText());
            submissionDate = cells.get(SUBMISSION_DATE_COLUMN).getText();
            dueDate = cells.get(DUE_DATE_COLUMN).getText();
            editButton = row.findElement(By.tagName("a"));
        }

        @Override
        public Long getId() {
            return id;
        }

        public AnnualRegistrationType getType() {
            return type;
        }

        public RegistrationStatus getStatus() {
            return status;
        }

        public String getSubmissionDate() {
            return submissionDate;
        }

        public String getDueDate() {
            return dueDate;
        }

        public OverviewTab clickEditButton() {
            editButton.click();
            return new OverviewTab(getDriver(), new AnnualRegistrationPage(getDriver()).waitUntilReady())
                    .waitUntilReady();
        }

        public String getEditButtonLabel() {
            return editButton.getText();
        }
    }

    public class FormListing implements TableListing {
        private final static int FORM_COLUMN = 0;
        private final static int OPTIONALITY_COLUMN = 1;
        private final static int LAST_UPDATE_DATE_COLUMN = 2;
        private final static int STATUS_COLUMN = 3;

        private final Long id;
        private final String form;
        private final WebElement formLink;
        private final FormOptionality optionality;
        private final String lastUpdateDate;
        private final FormStatus status;

        public FormListing(WebElement row) {
            id = Long.valueOf(WebElementUtils.getId(row));
            List<WebElement> cells = getCells(row);
            form = cells.get(FORM_COLUMN).getText();
            formLink = cells.get(FORM_COLUMN).findElement(By.tagName("a"));
            optionality = FormOptionality.getByDisplay(cells.get(OPTIONALITY_COLUMN).getText());
            lastUpdateDate = cells.get(LAST_UPDATE_DATE_COLUMN).getText();
            status = FormStatus.getByDisplay(cells.get(STATUS_COLUMN).getText());
        }

        @Override
        public Long getId() {
            return id;
        }

        public String getForm() {
            return form;
        }

        public AbstractAnnualRegistrationTab<?> clickFormLink() {
            formLink.click();
            return (AbstractAnnualRegistrationTab<?>) identifyDisplayedComponent(10,
                    SupplementalInvestigatorDataFormTab.getFactory(new AnnualRegistrationPage(getDriver())),
                    AnnualForm1572Tab.getFactory(new AnnualRegistrationPage(getDriver())),
                    AnnualAdditionalAttachmentsTab.getFactory(new AnnualRegistrationPage(getDriver())),
                    FinancialDisclosureTab.getFactory(new AnnualRegistrationPage(getDriver())));
        }

        public FormOptionality getOptionality() {
            return optionality;
        }

        public String getLastUpdateDate() {
            return lastUpdateDate;
        }

        public FormStatus getStatus() {
            return status;
        }
    }

}
