/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package by.bsu.servlet;

import java.util.HashMap;
import java.util.Map;

import by.bsu.controller.*;
import org.thymeleaf.web.IWebRequest;


public class ControllerMappings {


    private static Map<String, IController> controllersByURL;


    static {
        controllersByURL = new HashMap<String, IController>();
        controllersByURL.put("/welcome/", new HomeController());
        controllersByURL.put("/welcome/doctors/list", new DoctorController());
        controllersByURL.put("/welcome/appointments/list", new AppointmentController());
        controllersByURL.put("/welcome/doctors/add", new DoctorAddController());
    }



    public static IController resolveControllerForRequest(final IWebRequest request) {
        final String path = request.getPathWithinApplication();
        System.out.println(path);
        return controllersByURL.get(path);
    }

    private ControllerMappings() {
        super();
    }


}
