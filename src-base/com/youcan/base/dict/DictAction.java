package com.youcan.base.dict;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

public class DictAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	ResultSet resultSet = null;
    private String act = "";
    private String message = "";
    private int numDictMeta = 0;
    private int numDictData = 0;
    private int dmId = 0;
    private String dmName = "";
    private String dmLabel = "";
    private int ddId = 0;
    private String dataLabel = "";
    private String dataValue = "";
    private int grade = 0;
    private DictModel dictModel = null;
    private ArrayList<DictMetaDao> dictMetaList = null;
    private ArrayList<DictDataDao> dictDataList = null;
    
	public DictAction() {
    	dictModel = new DictModel();
    }

	@Override
	public String execute() throws Exception {
		return SUCCESS;
    }
	
	public String dictMeta() {
		if (act.equals("add")) {
			dictModel.addDictMeta(dmName, dmLabel);
		} else if (act.equals("delete")) {
			dictModel.deleteDictMeta(dmId);
		} else if (act.equals("edit")) {
			dictModel.editDictMeta(dmId, dmName, dmLabel);
		}
		this.setNumDictMeta(dictModel.getDictMetaNum());
		this.setDictMetaList(dictModel.getDictMetaList());

		if (act.equals("add") || act.equals("delete") || act.equals("edit")) {
			dictModel.createDictMetaSelect(this.getDictMetaList());
		}
		return SUCCESS;
	}

	public String dictData() {
		if (act.equals("add")) {
			dictModel.addDictData(dmId, dataLabel, dataValue, grade);
		} else if (act.equals("delete")) {
			dictModel.deleteDictData(dmId, ddId);
		} else if (act.equals("edit")) {
			dictModel.editDictData(ddId, dataLabel, dataValue, grade);
		}
		this.setNumDictData(dictModel.getDictDataNum(dmId));
		this.setDictDataList(dictModel.getDictDataList(dmId));

		if (act.equals("add") || act.equals("delete") || act.equals("edit")) {
			dictModel.createDictDataSelect(dictModel.getDictMeta(dmId), this.getDictDataList());
		}
		return SUCCESS;
	}

	public int getDmId() {
		return dmId;
	}

	public void setDmId(int dmId) {
		this.dmId = dmId;
	}

	public String getDmName() {
		return dmName;
	}

	public void setDmName(String dmName) {
		this.dmName = dmName;
	}

	public String getDmLabel() {
		return dmLabel;
	}

	public void setDmLabel(String dmLabel) {
		this.dmLabel = dmLabel;
	}

	public int getDdId() {
		return ddId;
	}

	public void setDdId(int ddId) {
		this.ddId = ddId;
	}

	public String getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNumDictMeta() {
		return numDictMeta;
	}

	public void setNumDictMeta(int numDictMeta) {
		this.numDictMeta = numDictMeta;
	}

	public int getNumDictData() {
		return numDictData;
	}

	public void setNumDictData(int numDictData) {
		this.numDictData = numDictData;
	}

	public ArrayList<DictMetaDao> getDictMetaList() {
		return dictMetaList;
	}

	public void setDictMetaList(ArrayList<DictMetaDao> dictMetaList) {
		this.dictMetaList = dictMetaList;
	}

	public ArrayList<DictDataDao> getDictDataList() {
		return dictDataList;
	}

	public void setDictDataList(ArrayList<DictDataDao> dictDataList) {
		this.dictDataList = dictDataList;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}