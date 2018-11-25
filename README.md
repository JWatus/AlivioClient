## Alivio - Debts Management Client Application :money_with_wings: :credit_card: 

#### This is example of client application which allows user to connect to MiCuenta API and manage his debts.

### Configuration 

This project use the following ports : 

| Server     | Port |
|------------|------|
| localhost  | 8080 |

### How does it work?

Application operations in sequence are:

1. Through this application user is sending request with <i>Debtor</i> object to MiCuenta API. Next debtor is validated 
and depending on his presence in database returned is response with status: 200 if debtor is found or 404 
otherwise.

2. Next step is request for balance. This client is sending SSN number and API response with all debts 
off this debtor.

3. In third request Alivio is sending <i>PaymentDeclaration</i> object. API gets from this 
ID (if user has chosen specific debt to be paid) of chosen debt and amount of money which will be paid.
In response API is sending <i>PaymentPlan</i> based on received amount. This object has list of 
<i>PlannedPayments</i> and appropriate message for user - how repayment system works is described 
in the next paragraph.

4. In the end client sends <i>PaymentConfirmation</i> with <i>CreditCard</i> which will be used to pay. 
In response API will send status 200 if list of payments for chosen debts has been updated, 400 if 
payment amount was not valid or 404 if debt with chosen id does not exist.

If You want to reset database after initialization and making operations of it, just open link
 [http://localhost:7000/reset](http://localhost:7000/reset).

### How repayment system works?

- If user choose specific debt this debt will be paid. If amount of payment money will be greater 
than this debt, except chosen one, debts will be paid respectively from the oldest one as long as payment 
amount will be greater then zero.
- If user does not choose any debt, debts will be paid respectively from the oldest one from the beginning.
- If payment amount will be greater than sum of all debts, all of them will be paid instantly and proper 
message will be send to client.
- If there will be no more debts to be paid, user will get message about no more debts to be paid off.
 
### Technology used to create and develop this application: 
- Java 8
- Spring Boot
- Spring Web Services
- Java Messaging Service
- Hibernate
- H2
- JUnit
- Swagger

### Enjoy :heavy_exclamation_mark:
