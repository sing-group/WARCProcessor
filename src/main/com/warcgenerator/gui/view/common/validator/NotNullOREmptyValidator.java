package com.warcgenerator.gui.view.common.validator;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.warcgenerator.gui.helper.ValidatorHelper;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.util.locale.LocaleChangeEvent;
import com.warcgenerator.gui.util.locale.LocaleChangeListener;

public class NotNullOREmptyValidator extends AbstractValidator
	implements LocaleChangeListener {
	private String locateMessage;
	
	public NotNullOREmptyValidator(JFrame parent, JTextField c, String message) {
		super(parent, c, message);
		this.locateMessage = message;
	}

	protected boolean validationCriteria(JComponent c) {
		String textToValidate = ((JTextField) c).getText();
		return ValidatorHelper.isNotNullOREmpty(textToValidate);
	}

	@Override
	public void localeChanged(LocaleChangeEvent e) {
		setMessage(Messages.getString(this.locateMessage));
	}
}
