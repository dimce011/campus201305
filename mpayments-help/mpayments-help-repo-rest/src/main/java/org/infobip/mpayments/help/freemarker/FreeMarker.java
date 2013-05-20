package org.infobip.mpayments.help.freemarker;

import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarker {
	public Configuration getCfg() {
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	Configuration cfg;
	Template template;

	public FreeMarker() {
		super();
		cfg = new Configuration();
	}

	public String process(Map<String, Object> input) {
		String str = null;
		try {

			cfg.setClassForTemplateLoading(this.getClass(), "/");
			template = cfg.getTemplate("templates/helloworld.ftl");

			StringWriter sv = new StringWriter();

			template.process(input, sv);
			str = sv.toString();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {

		}
		return str;
	}
}
