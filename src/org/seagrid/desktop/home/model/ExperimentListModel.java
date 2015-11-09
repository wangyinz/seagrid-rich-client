/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package org.seagrid.desktop.home.model;

import javafx.beans.property.*;
import org.apache.airavata.model.appcatalog.appinterface.ApplicationInterfaceDescription;
import org.apache.airavata.model.appcatalog.computeresource.ComputeResourceDescription;
import org.apache.airavata.model.error.AiravataClientException;
import org.apache.airavata.model.experiment.ExperimentSummaryModel;
import org.seagrid.desktop.apis.airavata.AiravataManager;
import org.seagrid.desktop.util.SEAGridContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ExperimentListModel {
    private final static Logger logger = LoggerFactory.getLogger(ExperimentListModel.class);

    private StringProperty id;
    private BooleanProperty checked;
    private StringProperty name;
    private StringProperty application;
    private StringProperty host;
    private StringProperty status;
    private ObjectProperty<LocalDateTime> createdTime;

    public ExperimentListModel(StringProperty id, BooleanProperty checked, StringProperty name, StringProperty application, StringProperty host,
                               StringProperty status, ObjectProperty<LocalDateTime> createdTime) {
        this.id = id;
        this.checked = checked;
        this.name = name;
        this.application = application;
        this.host = host;
        this.status = status;
        this.createdTime = createdTime;
    }

    public ExperimentListModel(){
        this.checked = new SimpleBooleanProperty(false);
        this.id = new SimpleStringProperty("test-id");
        this.name = new SimpleStringProperty("test-name");
        this.application = new SimpleStringProperty("test-application");
        this.host = new SimpleStringProperty("test-host");
        this.status = new SimpleStringProperty("test-status");
        this.createdTime = new SimpleObjectProperty<>(LocalDateTime.now());
    }

    public ExperimentListModel(ExperimentSummaryModel experimentSummaryModel){
        this.id = new SimpleStringProperty(experimentSummaryModel.getExperimentId());
        this.checked = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty(experimentSummaryModel.getName());
        if(experimentSummaryModel.getResourceHostId()!=null){
            ComputeResourceDescription resourceDescription = null;
            try {
                resourceDescription = AiravataManager.getInstance().getComputeResource(experimentSummaryModel.getResourceHostId());
                if(resourceDescription != null){
                    this.host = new SimpleStringProperty(resourceDescription.getHostName());
                }
            } catch (AiravataClientException e) {
                e.printStackTrace();
            }
        }
        if(experimentSummaryModel.getExecutionId()!=null){
            ApplicationInterfaceDescription interfaceDescription = null;
            try {
                interfaceDescription = AiravataManager.getInstance().getApplicationInterface(experimentSummaryModel.getExecutionId());
                if(interfaceDescription != null){
                    this.application = new SimpleStringProperty(interfaceDescription.getApplicationName());
                }
            } catch (AiravataClientException e) {
                e.printStackTrace();
            }
        }
        this.status = new SimpleStringProperty(experimentSummaryModel.getExperimentStatus());
        this.createdTime = new SimpleObjectProperty<>(LocalDateTime.ofEpochSecond(experimentSummaryModel
                .getCreationTime() / 1000, 0, SEAGridContext.getInstance().getTimeZoneOffset()));
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public boolean getChecked() {
        return checked.get();
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getApplication() {
        return application.get();
    }

    public StringProperty applicationProperty() {
        return application;
    }

    public void setApplication(String application) {
        this.application.set(application);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public LocalDateTime getCreatedTime() {
        return createdTime.get();
    }

    public ObjectProperty<LocalDateTime> createdTimeProperty() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime.set(createdTime);
    }
}