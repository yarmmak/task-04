package com.task04;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.events.SqsTriggerEventSource;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.resources.DependsOn;

import java.util.HashMap;
import java.util.Map;

import static com.syndicate.deployment.model.ResourceType.SQS_QUEUE;

@LambdaHandler(lambdaName = "sqs_handler",
		roleName = "sqs_handler-role"
)
@SqsTriggerEventSource(targetQueue = "async_queue", batchSize = 5)
@DependsOn(name = "async_queue", resourceType = SQS_QUEUE)
public class SqsHandler implements RequestHandler<Object, Map<String, Object>> {

	public Map<String, Object> handleRequest(Object request, Context context) {
		final LambdaLogger logger = context.getLogger();

		logger.log(String.format("Event: %s", request.toString()));

		return buildResponse();
	}

	private Map<String, Object> buildResponse()
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("statusCode", 200);
		resultMap.put("body", "SqsHandler: Event Successfully Processed");
		return resultMap;
	}
}