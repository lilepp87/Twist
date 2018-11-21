package com.tracfone.twist.flows;

import net.sf.sahi.client.Browser;

public abstract class Flow {

	protected Browser browser;

	public Flow clickH1(String target) {
		browser.heading1(target).click();
		return this;
	}
	
	public Flow typeInTextField(String targetField, String fieldValue) {
		browser.textbox(targetField).setValue(fieldValue);
		return this;
	}
	
	public Flow typeInTextArea(String targetField, String fieldValue) {
		browser.textarea(targetField).setValue(fieldValue);
		return this;
	}
	
	public Flow typeInNumberBox(String targetField, String fieldValue) {
		browser.numberbox(targetField).setValue(fieldValue);
		return this;
	}

	public Flow typeInPasswordField(String targetField, String fieldValue) {
		browser.password(targetField).setValue(fieldValue);
		return this;
	}

	public Flow selectRadioButton(String targetField) {
		browser.radio(targetField).click();
		return this;
	}

	public Flow chooseFromSelect(String targetField, String fieldValue) {
		browser.select(targetField).choose(fieldValue);
		return this;
	}

	public Flow pressSubmitButton(String targetField) {
		browser.submit(targetField).click();
		return this;
	}

	public Flow pressButton(String targetField) {
		browser.button(targetField).click();
		return this;
	}
	
	public Flow doublePressButton(String targetField) {
		browser.button(targetField).doubleClick();
		return this;
	}

	public Flow clickLink(String targetField) {
		browser.link(targetField).click();
		return this;
	}

	public Flow clickSpanMessage(String message) {
		browser.span(message).click();
		return this;
	}

	public Flow clickTableHeaderMessage(String message) {
		browser.tableHeader(message).click();
		return this;
	}

	public Flow clickCellMessage(String message) {
		browser.cell(message).click();
		return this;
	}

	public Flow confirmMessage(String message) {
		browser.expectConfirm(message, true);
		return this;
	}

	public Flow selectCheckBox(String targetField) {
		browser.checkbox(targetField).click();
		return this;
	}

	public Flow clickImage(String targetField) {
		browser.image(targetField).click();
		return this;
	}

	public Flow clickBoldMessage(String targetField) {
		browser.bold(targetField).click();
		return this;
	}

	public Flow clickHeadingMessage(String targetField) {
		browser.heading2(targetField).click();
		return this;
	}
	
	public Flow clickH3(String targetField) {
		browser.heading3(targetField).click();
		return this;
	}

	public Flow clickStrongMessage(String targetField) {
		browser.strong(targetField).click();
		return this;
	}

	public Flow clickDivMessage(String targetField) {
		browser.div(targetField).click();
		return this;
	}

	public Flow listItem(String targetField) {
		browser.listItem(targetField).click();
		return this;
	}

	public Flow navigateTo(String targetField) {
		browser.navigateTo(targetField);
		return this;
	}

	public Flow clickImageSubmitButton(String targetField) {
		browser.imageSubmitButton(targetField).click();
		return this;
	}

	public boolean passwordVisible(String field) {
		return browser.isVisible(browser.password(field));
	}

	public boolean radioVisible(String radioButton) {
		return browser.isVisible(browser.radio(radioButton));
	}

	public boolean linkVisible(String link) {
		return browser.isVisible(browser.link(link));
	}
	
	public boolean imageVisible(String image) {
		return browser.isVisible(browser.image(image));
	}

	public boolean divVisible(String div) {
		return browser.isVisible(browser.div(div));
	}

	public boolean labelVisible(String label) {
		return browser.isVisible(browser.label(label));
	}

	public boolean submitButtonVisible(String button) {
		return browser.isVisible(browser.submit(button));
	}

	public boolean buttonVisible(String button) {
		return browser.isVisible(browser.button(button));
	}

	public boolean textboxVisible(String textField) {
		return browser.isVisible(browser.textbox(textField));
	}

	public boolean strongVisible(String strong) {
		return browser.isVisible(browser.strong(strong));
	}

	public boolean h1Visible(String h1) {
		return browser.isVisible(browser.heading1(h1));
	}

	public boolean h2Visible(String h2) {
		return browser.isVisible(browser.heading2(h2));
	}

	public boolean h3Visible(String h3) {
		return browser.isVisible(browser.heading3(h3));
	}
	
	public boolean h4Visible(String h4) {
		return browser.isVisible(browser.heading4(h4));
	}

	public boolean checkboxVisible(String check) {
		return browser.isVisible(browser.checkbox(check));
	}

	public boolean selectionboxVisible(String select) {
		return browser.isVisible(browser.select(select));
	}

	public boolean spanVisible(String span) {
		return browser.isVisible(browser.span(span));
	}

	public boolean tableHeaderVisible(String header) {
		return browser.isVisible(browser.tableHeader(header));
	}

	public boolean listItemVisible(String item) {
		return browser.isVisible(browser.listItem(item));
	}

	public boolean cellVisible(String cell) {
		return browser.isVisible(browser.cell(cell));
	}

	public void focusOnText(String text) {
		browser.textbox(text).focus();
	}

	public Browser getBrowser() {
		return browser;
	}

}