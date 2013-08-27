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
package gov.nih.nci.firebird.web.action.sponsor.annual.representative;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import gov.nih.nci.firebird.security.UserSessionInformation;
import gov.nih.nci.firebird.cagrid.UserSessionInformationFactory;
import gov.nih.nci.firebird.data.AnnualRegistrationConfiguration;
import gov.nih.nci.firebird.data.FormOptionality;
import gov.nih.nci.firebird.data.FormType;
import gov.nih.nci.firebird.data.RegistrationFormSetConfiguration;
import gov.nih.nci.firebird.data.RegistrationType;
import gov.nih.nci.firebird.data.user.FirebirdUser;
import gov.nih.nci.firebird.data.user.UserRoleType;
import gov.nih.nci.firebird.exception.ValidationException;
import gov.nih.nci.firebird.service.annual.registration.AnnualRegistrationConfigurationService;
import gov.nih.nci.firebird.service.protocol.FormTypeService;
import gov.nih.nci.firebird.test.AnnualRegistrationConfigurationFactory;
import gov.nih.nci.firebird.test.FirebirdUserFactory;
import gov.nih.nci.firebird.web.action.FirebirdWebTestUtility;
import gov.nih.nci.firebird.web.test.AbstractWebTest;

import java.util.List;
import java.util.Map.Entry;

import org.apache.struts2.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;

public class RequiredFormsActionTest extends AbstractWebTest {

    @Inject
    private RequiredFormsAction action;
    @Inject
    private AnnualRegistrationConfigurationService mockAnnualRegistrationConfigurationService;
    @Inject
    private FormTypeService mockFormTypeService;
    private FirebirdUser user;
    private AnnualRegistrationConfiguration registrationConfiguration;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = FirebirdUserFactory.getInstance().create();
        UserSessionInformation userSessionInformation = UserSessionInformationFactory.getInstance().create(
                user.getUsername(), UserRoleType.CTEP_SPONSOR.getGroupName());
        FirebirdWebTestUtility.setCurrentUser(action, user);
        FirebirdWebTestUtility.setUpGridSessionInformation(action, userSessionInformation);
        registrationConfiguration = AnnualRegistrationConfigurationFactory.getInstanceWithId().create();
        when(mockAnnualRegistrationConfigurationService.getCurrentConfiguration())
                .thenReturn(registrationConfiguration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrepare_NotCtepSponsor() {
        FirebirdWebTestUtility.setUpGridSessionInformation(action,
                UserSessionInformationFactory.getInstance().create(user.getUsername()));
        action.prepare();
    }

    public void testPrepare() {
        action.prepare();
        verify(mockAnnualRegistrationConfigurationService).getCurrentConfiguration();
        verify(mockFormTypeService).getStandardConfigureableForms(RegistrationType.ANNUAL);
    }

    @Test
    public void testEnter() {
        assertEquals(ActionSupport.SUCCESS, action.enter());
    }

    @Test
    public void testGetStandardForms() {
        List<FormType> forms = Lists.newArrayList();
        when(mockFormTypeService.getStandardConfigureableForms(RegistrationType.ANNUAL)).thenReturn(forms);
        action.prepare();
        assertSame(action.getStandardForms(), forms);
    }

    @Test
    public void testGetGridtableData() throws JSONException {
        action.prepare();
        String json = action.getGridtableData();
        checkJsonForConfiguration(json, registrationConfiguration);
    }

    private void checkJsonForConfiguration(String json, AnnualRegistrationConfiguration registrationConfiguration) {
        for (Entry<FormType, FormOptionality> entrySet : registrationConfiguration.getFormSetConfiguration()
                .getFormOptionalities().entrySet()) {
            FormType formType = entrySet.getKey();
            FormOptionality formOptionality = entrySet.getValue();
            if (formOptionality == FormOptionality.SUPPLEMENTARY) {
                checkSupplementaryFormNotInJson(json, formType);
            } else {
                checkStandardFormValuesInJson(json, formType, formOptionality);
            }
        }
    }

    private void checkSupplementaryFormNotInJson(String json, FormType formType) {
        assertFalse(json.contains(formType.getName()));
    }

    private void checkStandardFormValuesInJson(String json, FormType formType, FormOptionality formOptionality) {
        assertTrue(json.contains(formType.getDescription()));
        assertTrue(json.contains(formType.getName()));
        assertTrue(json.contains(formType.getId().toString()));
        assertTrue(json.contains(formType.getSample().getId().toString()));
        assertTrue(json.contains(formOptionality.name()));
    }

    @Test
    public void testGetGridtableData_NoConfiguration() throws JSONException {
        String json = action.getGridtableData();
        assertEquals("[]", json);
    }

    @Test
    public void testSaveFormConfiguration() throws ValidationException {
        List<FormType> formTypes = registrationConfiguration.getFormSetConfiguration().getAssociatedFormTypes();
        when(mockFormTypeService.getStandardConfigureableForms(RegistrationType.ANNUAL)).thenReturn(formTypes);
        action.prepare();

        action.setRequiredFormIds(Lists.newArrayList(formTypes.get(0).getId()));
        action.setOptionalFormIds(Lists.newArrayList(formTypes.get(1).getId()));

        assertEquals(ActionSupport.NONE, action.saveFormConfiguration());

        ArgumentCaptor<AnnualRegistrationConfiguration> savedConfigurationCapture = ArgumentCaptor
                .forClass(AnnualRegistrationConfiguration.class);
        verify(mockAnnualRegistrationConfigurationService).validateAndSave(savedConfigurationCapture.capture());
        RegistrationFormSetConfiguration savedFormConfiguration = savedConfigurationCapture.getValue()
                .getFormSetConfiguration();
        assertEquals(FormOptionality.REQUIRED, savedFormConfiguration.getOptionality(formTypes.get(0)));
        assertEquals(FormOptionality.OPTIONAL, savedFormConfiguration.getOptionality(formTypes.get(1)));
        assertEquals(FormOptionality.NONE, savedFormConfiguration.getOptionality(formTypes.get(2)));
    }

    @Test
    public void testSaveFormConfiguration_ValidationException() throws ValidationException {
        action.prepare();
        when(mockAnnualRegistrationConfigurationService.validateAndSave(any(AnnualRegistrationConfiguration.class)))
                .thenThrow(new ValidationException());
        assertEquals(ActionSupport.INPUT, action.saveFormConfiguration());
    }

    @Test
    public void testGetStandardFormsJson() throws JSONException {
        List<FormType> forms = AnnualRegistrationConfigurationFactory.getInstanceWithId().create()
                .getFormSetConfiguration().getAssociatedFormTypes();
        when(mockFormTypeService.getStandardConfigureableForms(RegistrationType.ANNUAL)).thenReturn(forms);
        action.prepare();
        String actualJson = action.getStandardFormsJson();
        checkJsonForForms(actualJson, forms);
    }

    private void checkJsonForForms(String actualJson, List<FormType> forms) {
        for (FormType form : forms) {
            assertTrue(actualJson.contains(form.getId().toString()));
            assertTrue(actualJson.contains(form.getDescription()));
            assertTrue(actualJson.contains(form.getName()));
            assertTrue(actualJson.contains(form.getInvestigatorDefault().name()));
        }
    }

    @Test
    public void testSetSubInvestigatorText() {
        registrationConfiguration.setSubInvestigatorText(null);
        action.prepare();
        action.setSubInvestigatorText("text");
        assertEquals("text", action.getRegistrationConfiguration().getSubInvestigatorText());
        assertNull(registrationConfiguration.getSubInvestigatorText());
    }

    @Test
    public void testSetProtocolText() {
        action.prepare();
        action.setProtocolText("text");
        assertEquals("text", action.getRegistrationConfiguration().getProtocolText());
        assertNull(registrationConfiguration.getProtocolText());
    }

}