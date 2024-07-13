/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.controller.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.controller.dao.GenericDAO;
import br.com.controller.domain.Clifor;


@ManagedBean
public class AutoCompleteViewClifor {
    
    private Clifor selectedClifor;
    private Clifor clifor;
    
    @ManagedProperty("#{cliforService}")
    private List<Clifor> clifors;
    
    public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			results.add(query + i);
		}
		
		return results;
	}
    
    public List<Clifor> completeClifor(String query) {
        List<Clifor> allClifor = GenericDAO.findAll(Clifor.class);
		List<Clifor> filteredClifor = new ArrayList<Clifor>();
        
        for (int i = 0; i < allClifor.size(); i++) {
            clifor = allClifor.get(i);
            if(clifor.getNom().toString().toLowerCase().contains(query)) {
                filteredClifor.add(clifor);
            }
        }        
        return filteredClifor;
	}
    
    public List<Clifor> completeCliforContains(String query) {
        List<Clifor> allClifors = GenericDAO.findByField(Clifor.class, query, query);
		List<Clifor> filteredClifors = new ArrayList<Clifor>();
        
        for (int i = 0; i < allClifors.size(); i++) {
            clifor = allClifors.get(i);
            if(clifor.getNom().toLowerCase().contains(query)) {
                filteredClifors.add(clifor);
            }
        }
        
        return filteredClifors;
	}
        
    public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Selecionado", event.getObject().toString()));
    }

    public char getCliforGroup(Clifor Clifor) {
        return Clifor.getFantasia().charAt(0);
    }

	public Clifor getSelectedClifor() {
		return selectedClifor;
	}

	public void setSelectedClifor(Clifor selectedClifor) {
		this.selectedClifor = selectedClifor;
	}

	public Clifor getClifor() {
		return clifor;
	}

	public void setClifor(Clifor clifor) {
		this.clifor = clifor;
	}

	public List<Clifor> getClifors() {
		return clifors;
	}

	public void setClifors(List<Clifor> clifors) {
		this.clifors = clifors;
	}	
}
