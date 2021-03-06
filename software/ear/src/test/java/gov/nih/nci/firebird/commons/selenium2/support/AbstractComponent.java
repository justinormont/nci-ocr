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
package gov.nih.nci.firebird.commons.selenium2.support;

import static gov.nih.nci.firebird.commons.selenium2.util.WaitUtils.DEFAULT_WAIT_TIMEOUT_SECONDS;
import gov.nih.nci.firebird.commons.selenium2.util.FormUtils;
import gov.nih.nci.firebird.commons.selenium2.util.JavascriptUtils;
import gov.nih.nci.firebird.commons.selenium2.util.WaitUtils;
import gov.nih.nci.firebird.commons.selenium2.util.WebElementUtils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

public abstract class AbstractComponent {

    private final WebDriver driver;

    protected AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Returns true if the element(s) specified by the locator are currently on the page.
     * 
     * @param context the search context in which to look for the element(s)
     * @param locator the Selenium locator to for the element(s)
     * @return true if the element(s) are found.
     */
    protected static boolean isPresent(SearchContext context, By locator) {
        return WebElementUtils.isPresent(context, locator);
    }

    /**
     * Returns true if there are any elements specified by the locator currently visible on the page.
     *
     * @param context the search context in which to look for the element(s)
     * @param locator the Selenium locator to for the element(s)
     * @return true if any of the element(s) are visible.
     */
    protected static boolean isVisible(SearchContext context, By locator) {
        return WebElementUtils.isVisible(context, locator);
    }

    /**
     * Returns the current value of an input field.
     * 
     * @param input the input field
     * @return the current value.
     */
    protected static String getInputValue(WebElement input) {
        return FormUtils.getInputValue(input);
    }

    /**
     * Clears an input field and sends the input value as keystrokes
     * to the input.
     * 
     * @param input the input field
     * @param value the value
     */
    protected static void type(WebElement input, String value) {
        FormUtils.type(input, value);
    }

    /**
     * Selects a value from a single select element based on option value.
     * 
     * @param selectElement the SELECT web element
     * @param value the option value to select
     */
    protected static void select(WebElement selectElement, String value) {
        FormUtils.select(selectElement, value);
    }

    /**
     * Selects a value from a single select element based on option text.
     * 
     * @param selectElement the SELECT web element
     * @param value the option text to select
     */
    protected static void selectByVisibleText(WebElement selectElement, String value) {
        FormUtils.selectByVisibleText(selectElement, value);
    }

    /**
     * Selects a value from a single select element by option index.
     * 
     * @param selectElement the SELECT web element
     * @param index the option index
     */
    protected static void select(WebElement selectElement, int index) {
        FormUtils.select(selectElement, index);
    }

    /**
     * Returns the currently selected option value of a select element
     * 
     * @param select the SELECT WebElement
     * @return the selected value or null if none
     */
    protected static String getSelectedValue(WebElement select) {
        return FormUtils.getSelectedValue(select);
    }

    /**
     * Returns the currently selected option text of a select element
     * 
     * @param select the SELECT WebElement
     * @return the selected text or null if none
     */
    protected static String getSelectedText(WebElement select) {
        return FormUtils.getSelectedText(select);
    }

    /**
     * Returns a List of Strings by calling getText() on each element.
     * 
     * @param elements the elements
     * @return the list of text Strings contained in the elements.
     */
    protected static List<String> toStringList(List<WebElement> elements) {
        return WebElementUtils.toStringList(elements);
    }

    /**
     * Get's the id attribute from a WebElement.
     * 
     * @param element get id from this element
     * @return the id or null if not present
     */
    protected static String getId(WebElement element) {
        return WebElementUtils.getId(element);
    }

    /**
     * Returns a single element if it is present, null otherwise.
     * 
     * @param context the context to search
     * @param locator the locator to use to find the element
     * @return the element if found or null
     */
    protected static WebElement getElementIfPresent(SearchContext context, By locator) {
        return WebElementUtils.getElementIfPresent(context, locator);
    }

    /**
     * Returns a single element if it is visible, null otherwise.
     *
     * @param context the context to search
     * @param locator the locator to use to find the element
     * @return the element if found or null
     */
    protected static WebElement getElementIfVisible(SearchContext context, By locator) {
        return WebElementUtils.getElementIfVisible(context, locator);
    }

    /**
     * Pause execution for a specified duration. For rare instances where a condition can't be used to wait for an
     * expected component state.
     *
     * @param milliseconds pause for this duration in milliseconds
     */
    protected static void pause(long milliseconds) {
        WaitUtils.pause(milliseconds);
    }

    /**
     * Returns the WebDriver.
     * 
     * @return the driver.
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Waits for the element or elements indicated by locator to appear
     * on the page using the default timeout of 5 seconds.
     * 
     * @param locator locator for element(s) to wait for
     */
    protected void waitFor(By locator) {
        WaitUtils.waitFor(getDriver(), locator);
    }

    /**
     * Waits for the element or elements indicated by locator to appear
     * on the page.
     * 
     * @param locator locator for element(s) to wait for
     * @param timeoutSeconds timeout after this duration in seconds
     */
    protected void waitFor(By locator, int timeoutSeconds) {
        WaitUtils.waitFor(getDriver(), locator, timeoutSeconds);
    }

    /**
     * Waits for a condition to evaluate to true or return a WebElement using the default timeout.
     * 
     * @param condition wait for this condition
     */
    protected void waitFor(ExpectedCondition<?> condition) {
        WaitUtils.waitFor(getDriver(), condition);
    }

    /**
     * Waits for a condition to evaluate to true or return a WebElement.
     * 
     * @param condition wait for this condition
     * @param timeoutSeconds timeout after this duration in seconds
     */
    protected void waitFor(ExpectedCondition<?> condition, int timeoutSeconds) {
        WaitUtils.waitFor(getDriver(), condition, timeoutSeconds);
    }

    /**
     * Waits until the given Javascript expression evaluates to true using the default timeout.
     * 
     * @param javascript wait until this evaluates to true
     */
    protected void waitForJavascriptCondition(String javascript) {
        JavascriptUtils.waitForJavascriptCondition(getDriver(), javascript);
    }

    /**
     * Executes a Javascript expression and returns the result
     * 
     * @param javascript the script to execute
     * @param args arguments to the script
     * @return the result
     */
    protected Object executeJavascript(String javascript, Object... args) {
        return JavascriptUtils.execute(driver, javascript, args);
    }
    
    /**
     * Returns true if the element(s) specified by the locator are currently on the page.
     * 
     * @param locator the Selenium locator to for the element(s)
     * @return true if the element(s) are found.
     */
    protected boolean isPresent(By locator) {
        return isPresent(getDriver(), locator);
    }

    /**
     * Locates a single element in the current page.
     * 
     * @param locator locator for the element
     * @return the element
     */
    protected WebElement findElement(By locator) {
        return getDriver().findElement(locator);
    }

    /**
     * Locates multiple elements in the current page.
     * 
     * @param locator locator for the elements
     * @return the element list
     */
    protected List<WebElement> findElements(By locator) {
        return getDriver().findElements(locator);
    }

    /**
     * Returns the currently present component as soon as one that is identifiable by one of the 
     * factories is displayed.
     * 
     * @param factories the component identifying factories to use
     * @return the identified component
     */
    protected AbstractLoadableComponent<?> identifyDisplayedComponent(IdentifiableComponentFactory<?>... factories) {
        return identifyDisplayedComponent(DEFAULT_WAIT_TIMEOUT_SECONDS, factories);
    }

    /**
     * Returns the currently present component as soon as one that is identifiable by one of the 
     * factories is displayed.
     * 
     * @param timeoutSeconds timeout in seconds
     * @param factories the component identifying factories to use
     * @return the identified component
     */
    protected AbstractLoadableComponent<?> identifyDisplayedComponent(int timeoutSeconds, IdentifiableComponentFactory<?>... factories) {
        ComponentIdentifier identifyingFactory = new ComponentIdentifier(getDriver(), timeoutSeconds, factories);
        return identifyingFactory.getDisplayedComponent();
    }

}
