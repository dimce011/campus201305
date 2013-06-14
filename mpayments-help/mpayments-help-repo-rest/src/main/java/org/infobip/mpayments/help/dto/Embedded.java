package org.infobip.mpayments.help.dto;

import java.util.List;

public class Embedded {
	
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}
	public void setParagraphs(List<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}

	List <Paragraph> paragraphs;
}
