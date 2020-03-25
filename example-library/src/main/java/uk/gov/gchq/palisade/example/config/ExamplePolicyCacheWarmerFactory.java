/*
 * Copyright 2020 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.palisade.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.common.ExampleUsers;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.rule.BankDetailsRule;
import uk.gov.gchq.palisade.example.rule.DutyOfCareRule;
import uk.gov.gchq.palisade.example.rule.FirstResourceRule;
import uk.gov.gchq.palisade.example.rule.NationalityRule;
import uk.gov.gchq.palisade.example.rule.RecordMaskingRule;
import uk.gov.gchq.palisade.example.rule.ZipCodeMaskingRule;
import uk.gov.gchq.palisade.service.CacheWarmerFactory;
import uk.gov.gchq.palisade.service.request.Policy;

import java.util.Map;
import java.util.Objects;

@ConfigurationProperties
public class ExamplePolicyCacheWarmerFactory implements CacheWarmerFactory {

    private String type;
    private String owner;
    private Map<String, String> resourceRules;
    private Map<String, String> recordRules;

    public ExamplePolicyCacheWarmerFactory() {
    }

    public ExamplePolicyCacheWarmerFactory(final String type, final String owner, final Map<String, String> resourceRules, final Map<String, String> recordRules) {
        this.type = type;
        this.owner = owner;
        this.resourceRules = resourceRules;
        this.recordRules = recordRules;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public Map<String, String> getResourceRules() {
        return resourceRules;
    }

    public void setResourceRules(final Map<String, String> resourceRules) {
        this.resourceRules = resourceRules;
    }

    public Map<String, String> getRecordRules() {
        return recordRules;
    }

    public void setRecordRules(final Map<String, String> recordRules) {
        this.recordRules = recordRules;
    }

    @Override
    public User userWarm() {
        return null;
    }

    @Override
    public Policy<Employee> policyWarm() {
        Policy<Employee> policy = new Policy<>();
        if ("Alice".equals(getOwner())) {
            policy.owner(ExampleUsers.getAlice());
        }
        if ("Bob".equals(getOwner())) {
            policy.owner(ExampleUsers.getBob());
        }
        if ("Eve".equals(getOwner())) {
            policy.owner(ExampleUsers.getEve());
        }
        for (String key : resourceRules.keySet()) {
            createRule(policy, key, resourceRules.get(key));
        }
        for (String key : recordRules.keySet()) {
            createRule(policy, key, recordRules.get(key));
        }

        return policy;
    }

    private void createRule(final Policy<Employee> policy, final String message, final String rule) {
        switch (rule) {
            case "BankDetailsRule":
                policy.recordLevelRule(message, new BankDetailsRule());
                break;
            case "DutyOfCareRule":
                policy.recordLevelRule(message, new DutyOfCareRule());
                break;
            case "NationalityRule":
                policy.recordLevelRule(message, new NationalityRule());
                break;
            case "ZipCodeMaskingRule":
                policy.recordLevelRule(message, new ZipCodeMaskingRule());
                break;
            case "RecordMaskingRule":
                policy.recordLevelRule(message, new RecordMaskingRule());
                break;
            case "FirstResourceRule":
                policy.resourceLevelRule(message, new FirstResourceRule());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExamplePolicyCacheWarmerFactory that = (ExamplePolicyCacheWarmerFactory) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(resourceRules, that.resourceRules) &&
                Objects.equals(recordRules, that.recordRules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, owner, resourceRules, recordRules);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExamplePolicies{\n");
        sb.append("\t\ttype=").append(type).append("\n");
        sb.append("\t\towner=").append(owner).append("\n");
        sb.append("\t\tresourceRules=").append(resourceRules).append("\n");
        sb.append("\t\trecordRules=").append(recordRules).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
