/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.domain.userprojects.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.openengsb.core.api.Constants;
import org.openengsb.core.api.model.annotation.Model;
import org.openengsb.core.api.model.annotation.OpenEngSBModelId;
import org.openengsb.core.api.security.model.Permission;
import org.openengsb.labs.delegation.service.Provide;

import com.google.common.collect.Lists;

/**
 * Note: To make sure that this object can be managed properly by the EDB it is recommended to call the generateUuid
 * method after setting all object attributes.
 */
@Provide(context = { Constants.DELEGATION_CONTEXT_MODELS })
@Model
public class Assignment {

    @OpenEngSBModelId
    private String uuid;

    private String user;
    private String project;

    private List<String> roles = Lists.newArrayList();
    private List<Permission> permissions = Lists.newArrayList();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        generateUuid();
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
        generateUuid();
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean generateUuid() {

        if (user == null || project == null) {
            return false;
        } else {
            uuid = "Assign+" + user + "+" + project;

            return true;
        }
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", user, project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, project, roles, permissions);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Assignment)) {
            return false;
        }
        final Assignment other = (Assignment) obj;
        return Objects.equals(user, other.user) && Objects.equals(project, other.project)
            && Objects.equals(roles, other.roles) && Objects.equals(permissions, other.permissions);
    }
}
