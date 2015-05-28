package com.nagesoft.postcode.board.model;

import java.beans.PropertyEditorSupport;

public class BoardTypeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		BoardType type = BoardType.getBoardType(text);

		setValue(type);
	}

	@Override
	public String getAsText() {
		BoardType type = (BoardType)getValue();

		return type.getCode();
	}

}
