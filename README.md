# Martin Libersan AppDirect Coding Challenge Web Application

This is the Martin Libersan AppDirect Coding Challenge web application that uses Spring MVC and Hibernate. 

## Running the application locally

First build with:

    $mvn clean install

Then run it with:

    $java -jar target/dependency/webapp-runner.jar target/*.war


## See the application deployed on Heroku

[http://mlibersan-coding-challenge.herokuapp.com/](http://mlibersan-coding-challenge.herokuapp.com/)

## AppDirect Integration : Login URL  

	https://mlibersan-coding-challenge.herokuapp.com/login?openidUrl={openid}

## AppDirect Integration : Endpoint URLs

	SUBSCRIPTION_ORDER  :  https://mlibersan-coding-challenge.herokuapp.com/subscription/order?endpointUrl={eventUrl}
	SUBSCRIPTION_CANCEL :  https://mlibersan-coding-challenge.herokuapp.com/subscription/cancel?endpointUrl={eventUrl}
	USER_ASSIGNMENT     :  https://mlibersan-coding-challenge.herokuapp.com/user/assignment?endpointUrl={eventUrl}
	USER_UNASSIGNMENT   :  https://mlibersan-coding-challenge.herokuapp.com/user/unassignment?endpointUrl={eventUrl}

## AppDirect Integration : OAuth Configuration

OAuth Consumer Key and OAuth Consumer Secret are define has constants in the [MainController](https://github.com/mlibersan/appdirect-coding-challenge/blob/master/src/main/java/com/martinlibersan/appdirect/codingchallenge/web/controller/MainController.java) of the web application.  

Note: If you integrate this application using your on AppDirect developper account you must change the OAuth Consumer Key and OAuth Consumer Secret and redeploy the application.

