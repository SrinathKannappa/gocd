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

import com.thoughtworks.go.plugin.api.response.execution.ExecutionResult;
import com.thoughtworks.go.plugin.api.task.Task;
import com.thoughtworks.go.plugin.infra.Action;
import com.thoughtworks.go.plugin.infra.ActionWithReturn;
import com.thoughtworks.go.plugin.infra.PluginManager;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ApiBasedTaskExtensionTest {
    @Test
    public void shouldExecuteTheTask() {
        PluginManager pluginManager = mock(PluginManager.class);
        ApiBasedTaskExtension extension = new ApiBasedTaskExtension(pluginManager);
        String pluginId = "plugin-id";
        final ActionWithReturn actionWithReturn = mock(ActionWithReturn.class);
        when(pluginManager.doOn(Task.class, pluginId, actionWithReturn)).thenReturn(ExecutionResult.failure("failed"));

        ExecutionResult executionResult = extension.execute(pluginId, actionWithReturn);

        verify(pluginManager).doOn(Task.class, pluginId, actionWithReturn);
        assertThat(executionResult.getMessagesForDisplay(), is("failed"));
        assertFalse(executionResult.isSuccessful());
    }

    @Test
    public void shouldGetApiBasedTask(){
        PluginManager pluginManager = mock(PluginManager.class);
        ApiBasedTaskExtension extension = new ApiBasedTaskExtension(pluginManager);
        String pluginId = "plugin-id";
        final Action action = mock(Action.class);

        extension.doOnTask(pluginId, action);

        verify(pluginManager).doOn(Task.class, pluginId, action);
    }

}