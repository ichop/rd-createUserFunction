package com.rd.project;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.rd.project.model.User;
import com.rd.project.service.UserService;
import com.rd.project.utils.Request;


public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    private static final UserService userService;


    static {
        userService = new UserService();
    }


    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent requestEvent, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            Gson gson = new Gson();

            Request request = gson.fromJson(requestEvent.getBody(), Request.class);

            String name = request.getName();
            String surname = request.getSurname();
            String email = request.getEmail();
            String role = request.getRole();

            User user = new User(email,name,surname,role);

            int id = userService.createUser(user);

            String output = String.format("{ \"message\": \"User created\", \"id\": \"%s\" }", id);

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            return response
                    .withBody(e.getMessage())
                    .withStatusCode(500);
        }
    }
}
