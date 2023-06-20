# TFA Test Automation

## Getting OTPs for 2FA 
We use SMSGlobal and Mailinator APIs to grab OTPs from sms and email sent. 

### SMS 
For sms use `GetOtpForPhoneNumber` method `Framework/utilities/SmsGlobalHelper`.

This takes 3 params - `phone number`, `set of chars left & right of the OTP number sent within message. 

Usually TFA uses standard template for sending these sms - these standard template chars are added in the `configSettings.properties` file. 
So as long your project uses these standard chars you can pass `null` params - this method will grab them from config. 
Else calling method need pass specific left right chars as per project requirements.

Phone number must be passed as `61xxxxxxxxx`. If you pass with `+` symbol - method will pop it out. Phone number must not be passed in `04xxxxxxxx` format.

### Email
For email use `MailinatorHelper` in `Framework/utilities`

We use Mailintor single inbox domain `tfasqa.testinator.com` - so all the email should include this. Use `CreateEmailId` method to get one.

Use `GetOtpFromEmail` to get OTP as string or `GetOtpFromEmailAsArray` to get OTPs in array - useful for apps like VFM.

Both of the method takes 5 arguments - `email`, `charsToLeftOfOtp`, `charsToRightOfOtp`, `additionalFilters` and `returnAsArray` as boolean

`charsToLeftOfOtp`, `charsToRightOfOtp` needed to extract OTP from the message body. If our project uses standard template as mentioned in `configSettings.properties` file,
then you can pass `null`. Else specify specific chars as per your project requirement. 

Sometimes projects can send additional emails to same email ID such as activations, quotes etc., immediately -in that case we need to add extra filters 
to filter OTP specific email. In that situation send additional filter such as `subject`. You can use standard template mentioned in the `configSettings.properties`. 
`Your One Time Password (OTP) for %s` as subject filter

Here is an example 
```
  var filters = new HashMap<String, String>();
        filters.put("subject", ConfigHelper.getConfigValue(TOYOTA_EMAIL_OTP_SUBJECT_TEMPLATE).formatted("VFM"));
        var otp = GetOtpFromEmail("vfmtester@tfsqa.testinator.com", null, null, filters,false);
```

Possible filter keys are `subject`, `origfrom`, `from`, `id` and `source`

If you need to get the OTP as array `returnAsArray` as `true`. Once you get them as object array, use something like below to convert to string array.

```
       var otp = GetOtpFromEmail("vfmtester@tfsqa.testinator.com", null, null, null, true);
        for (var val: (String[])otp) {
            System.out.println(val);
        }
```

#### Note: 
Ensure to call `DeleteEmail` method afterwards to delete the email from Mailinator inbox. This is to avoid over crowding the inbox and high volume of response.

## Xray Integration
After tests run, results can be uploaded to  [Master Test Dashboard](https://tfal.atlassian.net/jira/dashboards/10175) using `XrayExporter` utility under `Framework/utilities`.

There are 2 steps.

First in the project runner class add @AfterSuite and implement the results export after all tests run. Here is a code snippet:
```
    @AfterSuite
    @Parameters({"applicationName"})
    public void afterTests(String applicationName) {
        XrayExporter.PostResultsToXray(applicationName, CUCUMBER_TEST_RESULT_JSON_FILE_PATH);
    }
```
Then add `applicationName` as a parameter in `TestNG XML` so that  project details can be uploaded to Xray

`<parameter name="applicationName" value="DLO"/>`.

Here `DLO` as an example - change as per your project need.

That's it. After all tests completed, test results for all tests with correct tag will be exported to  Xray.

PS: Test features should be exactly tagged as key name in Xray.

For example: Here `MAR-457` is the xray key for the test https://tfal.atlassian.net/browse/MAR-457
```gherkin
  @api @TEST_MAR-457
  Scenario Outline: Calculate Repayment with balloon 1000 and deposit amount 1000 for 36 months frequency monthly with different quotes
```
