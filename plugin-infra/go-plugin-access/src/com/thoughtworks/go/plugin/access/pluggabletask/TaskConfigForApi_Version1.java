/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.plugin.access.pluggabletask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.config.Property;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskConfigProperty;
import com.thoughtworks.go.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskConfigForApi_Version1 {
    public String toJson(TaskConfig taskConfig) {
        ArrayList<Map> properties = new ArrayList<Map>();
        for (Property property : taskConfig.list()) {
            HashMap prop = new HashMap();
            prop.put("key", property.getKey());
            prop.put("value", property.getValue());
            prop.put("secure", property.getOption(Property.SECURE));
            prop.put("required", property.getOption(Property.REQUIRED));
            prop.put("display-name", property.getOption(Property.DISPLAY_NAME));
            prop.put("display-order", property.getOption(Property.DISPLAY_ORDER).toString());
            properties.add(prop);
        }
        return new Gson().toJson(properties);
    }

    public TaskConfig fromJson(String configJson) {
        List<Map> list = (List<Map>) new GsonBuilder().create().fromJson(configJson, Object.class);
        TaskConfig taskConfig = new TaskConfig();
        for (Map map : list) {
            TaskConfigProperty property = new TaskConfigProperty((String) map.get("key"), null);
            property.withDefault((String) map.get("default-value"));
            property.with(Property.SECURE, (Boolean) map.get("secure"));
            property.with(Property.REQUIRED, (Boolean) map.get("required"));
            property.with(Property.DISPLAY_NAME, (String) map.get("display-name"));
            if (!StringUtil.isBlank((String) map.get("display-order"))) {
                property.with(Property.DISPLAY_ORDER, Integer.parseInt((String) map.get("display-order")));
            }
            taskConfig.add(property);
        }
        return taskConfig;
    }
}
