package br.com.controller.galaxpay.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.controller.domain.Clifor;
import br.com.controller.domain.Duprec;
import br.com.controller.services.EnviarWA;

public class EnviarEmailConfirmacao {

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	Date dt = new Date();

	String from = "financeiro@miklus.com.br";
	final String username = "financeiro@miklus.com.br";// change accordingly
	final String password = "fin478";// change accordingly

	public void enviarConfirmacao(String email, String whatsApp, float totalBoletos, int qtdeBoletos)
			throws IOException {
		
		System.out.println("Email: "+email);
		// Recipient's email ID needs to be mentioned.
		String to = email;

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.miklus.com.br";

		Properties props = new Properties();
		props.put("mail.smtp.user", "financeiro@miklus.com.br");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Conferencia de Boletos GalaxPay");

			// Send the actual HTML message, as big as you like
			message.setContent(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
							+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"font-family:arial, 'helvetica neue', helvetica, sans-serif\">\r\n"
							+ "<head>\r\n" + "<meta charset=\"UTF-8\">\r\n"
							+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\r\n"
							+ "<meta name=\"x-apple-disable-message-reformatting\">\r\n"
							+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
							+ "<meta content=\"telephone=no\" name=\"format-detection\">\r\n"
							+ "<title>Novo Template</title><!--[if (mso 16)]>\r\n" + "<style type=\"text/css\">\r\n"
							+ "a {text-decoration: none;}\r\n" + "</style>\r\n"
							+ "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\r\n"
							+ "<xml>\r\n" + "<o:OfficeDocumentSettings>\r\n" + "<o:AllowPNG></o:AllowPNG>\r\n"
							+ "<o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + "</o:OfficeDocumentSettings>\r\n"
							+ "</xml>\r\n" + "<![endif]--><!--[if !mso]><!-- -->\r\n"
							+ "<link href=\"https://fonts.googleapis.com/css2?family=Oswald:wght@500&display=swap\" rel=\"stylesheet\"><!--<![endif]-->\r\n"
							+ "<style type=\"text/css\">\r\n" + "#outlook a {\r\n" + "padding:0;\r\n" + "}\r\n"
							+ ".es-button {\r\n" + "mso-style-priority:100!important;\r\n"
							+ "text-decoration:none!important;\r\n" + "}\r\n" + "a[x-apple-data-detectors] {\r\n"
							+ "color:inherit!important;\r\n" + "text-decoration:none!important;\r\n"
							+ "font-size:inherit!important;\r\n" + "font-family:inherit!important;\r\n"
							+ "font-weight:inherit!important;\r\n" + "line-height:inherit!important;\r\n" + "}\r\n"
							+ ".es-desk-hidden {\r\n" + "display:none;\r\n" + "float:left;\r\n" + "overflow:hidden;\r\n"
							+ "width:0;\r\n" + "max-height:0;\r\n" + "line-height:0;\r\n" + "mso-hide:all;\r\n"
							+ "}\r\n" + "[data-ogsb] .es-button {\r\n" + "border-width:0!important;\r\n"
							+ "padding:10px 30px 10px 30px!important;\r\n" + "}\r\n"
							+ "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120% } h1 { font-size:48px!important; text-align:center } h2 { font-size:24px!important; text-align:center } h3 { font-size:20px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:48px!important; text-align:center } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:24px!important; text-align:center } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:12px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:18px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0!important } .es-m-p0r { padding-right:0!important } .es-m-p0l { padding-left:0!important } .es-m-p0t { padding-top:0!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-m-p5 { padding:5px!important } .es-m-p5t { padding-top:5px!important } .es-m-p5b { padding-bottom:5px!important } .es-m-p5r { padding-right:5px!important } .es-m-p5l { padding-left:5px!important } .es-m-p10 { padding:10px!important } .es-m-p10t { padding-top:10px!important } .es-m-p10b { padding-bottom:10px!important } .es-m-p10r { padding-right:10px!important } .es-m-p10l { padding-left:10px!important } .es-m-p15 { padding:15px!important } .es-m-p15t { padding-top:15px!important } .es-m-p15b { padding-bottom:15px!important } .es-m-p15r { padding-right:15px!important } .es-m-p15l { padding-left:15px!important } .es-m-p20 { padding:20px!important } .es-m-p20t { padding-top:20px!important } .es-m-p20r { padding-right:20px!important } .es-m-p20l { padding-left:20px!important } .es-m-p25 { padding:25px!important } .es-m-p25t { padding-top:25px!important } .es-m-p25b { padding-bottom:25px!important } .es-m-p25r { padding-right:25px!important } .es-m-p25l { padding-left:25px!important } .es-m-p30 { padding:30px!important } .es-m-p30t { padding-top:30px!important } .es-m-p30b { padding-bottom:30px!important } .es-m-p30r { padding-right:30px!important } .es-m-p30l { padding-left:30px!important } .es-m-p35 { padding:35px!important } .es-m-p35t { padding-top:35px!important } .es-m-p35b { padding-bottom:35px!important } .es-m-p35r { padding-right:35px!important } .es-m-p35l { padding-left:35px!important } .es-m-p40 { padding:40px!important } .es-m-p40t { padding-top:40px!important } .es-m-p40b { padding-bottom:40px!important } .es-m-p40r { padding-right:40px!important } .es-m-p40l { padding-left:40px!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\r\n"
							+ "</style>\r\n" + "</head>\r\n"
							+ "<body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n"
							+ "<div class=\"es-wrapper-color\" style=\"background-color:#9E6C5A\"><!--[if gte mso 9]>\r\n"
							+ "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n"
							+ "<v:fill type=\"tile\" color=\"#9E6C5A\"></v:fill>\r\n" + "</v:background>\r\n"
							+ "<![endif]-->\r\n"
							+ "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#9E6C5A\">\r\n"
							+ "<tr>\r\n" + "<td valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
							+ "<tr>\r\n" + "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table bgcolor=\"#ffffff\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FEF3E1;width:600px\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"left\" background=\"https://edfnjh.stripocdn.email/content/guids/CABINET_94a60be327b78ddc1c9aafb78f5d3fad911b5aa0b63d4770305d686dba6934ca/images/modernize_pequeno_png.png\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-bottom:30px;padding-top:40px;background-image:url(https://edfnjh.stripocdn.email/content/guids/CABINET_94a60be327b78ddc1c9aafb78f5d3fad911b5aa0b63d4770305d686dba6934ca/images/modernize_pequeno_png.png);background-repeat:no-repeat;background-position:left top\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n"
							+ "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" background=\"https://edfnjh.stripocdn.email/content/guids/CABINET_94a60be327b78ddc1c9aafb78f5d3fad911b5aa0b63d4770305d686dba6934ca/images/modernize_pequeno_png.png\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-image:url(https://edfnjh.stripocdn.email/content/guids/CABINET_94a60be327b78ddc1c9aafb78f5d3fad911b5aa0b63d4770305d686dba6934ca/images/modernize_pequeno_png.png);background-repeat:repeat;background-position:center top\">\r\n"
							+ "<tr>\r\n" + "<td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
							+ "<tr>\r\n" + "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:30px\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:300px\" valign=\"top\"><![endif]-->\r\n"
							+ "<table cellspacing=\"0\" cellpadding=\"0\" align=\"left\" class=\"es-left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"left\" class=\"es-m-p20b\" style=\"padding:0;Margin:0;width:300px\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:15px\"><h1 style=\"Margin:0;line-height:46px;mso-line-height-rule:exactly;font-family:Oswald, sans-serif;font-size:38px;font-style:normal;font-weight:bold;color:#363E44\">Para conferencia</h1></td>\r\n"
							+ "</tr>\r\n" + "<tr>\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#363E44;font-size:16px\"><br></p><br>Ola, neste envio de boletos, foram gravados no Galax Pay:<br><strong>"
							+ qtdeBoletos + "</strong> boletos no valor de R$<strong> " + totalBoletos
							+ "</strong><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#363E44;font-size:16px\"><br>Horario da geracao: "
							+ sdf2.format(dt) + "</p></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:240px\" valign=\"top\"><![endif]-->\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
							+ "<tr>\r\n"
							+ "<td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:240px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#363E44;font-size:16px\"><img class=\"adapt-img\" src=\"https://edfnjh.stripocdn.email/content/guids/CABINET_19c796e4cc83dd2f016022a3be193556/images/illustration_KyZ.png\" alt=\"startup\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"240\" title=\"startup\"></a></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table><!--[if mso]></td></tr></table><![endif]--></td>\r\n" + "</tr>\r\n" + "<tr>\r\n"
							+ "<td align=\"left\" bgcolor=\"#fef3e1\" style=\"Margin:0;padding-bottom:20px;padding-left:20px;padding-right:20px;padding-top:30px;background-color:#fef3e1\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n" + "<td align=\"left\" style=\"padding:0;Margin:0;width:560px\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#363E44;font-size:16px\">Se nao estiver correto, verifique os e-mails enviados anteriormente para conferir cada um dos que nao foram confirmados<br><br>Weldes - Lindo<br></p></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
							+ "<tr>\r\n" + "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table bgcolor=\"#ffffff\" class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\">\r\n"
							+ "<tr>\r\n"
							+ "<td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n" + "<td align=\"left\" style=\"padding:0;Margin:0;width:560px\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr>\r\n" + "<td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "</div>\r\n" + "</body>\r\n" + "</html>",
					"text/html");

			// Send message
			if(qtdeBoletos > 0) {
			Transport.send(message);

			EnviarWA enviarWA = new EnviarWA();
			
			//enviarWA.enviarMensagemWaTotal(totalBoletos, qtdeBoletos, whatsApp);
			}

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void enviarConfirmacaoDeErro(Duprec dup, String email, String whatsApp, String body) throws IOException {

		String to = email;

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.miklus.com.br";

		Properties props = new Properties();
		props.put("mail.smtp.user", "financeiro@miklus.com.br");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Boleto nao enviado GalaxPay");

			// Send the actual HTML message, as big as you like
			message.setContent(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
							+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n"
							+ "<head>\r\n" + "<meta charset=\"UTF-8\">\r\n"
							+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\r\n"
							+ "<meta name=\"x-apple-disable-message-reformatting\">\r\n"
							+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
							+ "<meta content=\"telephone=no\" name=\"format-detection\">\r\n"
							+ "<title>Novo e-mail</title><!--[if (mso 16)]>\r\n" + "<style type=\"text/css\">\r\n"
							+ "a {text-decoration: none;}\r\n" + "</style>\r\n"
							+ "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\r\n"
							+ "<xml>\r\n" + "<o:OfficeDocumentSettings>\r\n" + "<o:AllowPNG></o:AllowPNG>\r\n"
							+ "<o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + "</o:OfficeDocumentSettings>\r\n"
							+ "</xml>\r\n" + "<![endif]-->\r\n" + "<style type=\"text/css\">\r\n"
							+ ".rollover:hover .rollover-first {\r\n" + "max-height:0px!important;\r\n"
							+ "display:none!important;\r\n" + "}\r\n" + ".rollover:hover .rollover-second {\r\n"
							+ "max-height:none!important;\r\n" + "display:block!important;\r\n" + "}\r\n"
							+ "#outlook a {\r\n" + "padding:0;\r\n" + "}\r\n" + ".ExternalClass {\r\n"
							+ "width:100%;\r\n" + "}\r\n" + ".ExternalClass,\r\n" + ".ExternalClass p,\r\n"
							+ ".ExternalClass span,\r\n" + ".ExternalClass font,\r\n" + ".ExternalClass td,\r\n"
							+ ".ExternalClass div {\r\n" + "line-height:100%;\r\n" + "}\r\n" + ".es-button {\r\n"
							+ "mso-style-priority:100!important;\r\n" + "text-decoration:none!important;\r\n" + "}\r\n"
							+ "a[x-apple-data-detectors] {\r\n" + "color:inherit!important;\r\n"
							+ "text-decoration:none!important;\r\n" + "font-size:inherit!important;\r\n"
							+ "font-family:inherit!important;\r\n" + "font-weight:inherit!important;\r\n"
							+ "line-height:inherit!important;\r\n" + "}\r\n" + ".es-desk-hidden {\r\n"
							+ "display:none;\r\n" + "float:left;\r\n" + "overflow:hidden;\r\n" + "width:0;\r\n"
							+ "max-height:0;\r\n" + "line-height:0;\r\n" + "mso-hide:all;\r\n" + "}\r\n"
							+ "[data-ogsb] .es-button {\r\n" + "border-width:0!important;\r\n"
							+ "padding:10px 20px 10px 20px!important;\r\n" + "}\r\n"
							+ "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:left } h2 { font-size:26px!important; text-align:left } h3 { font-size:20px!important; text-align:left } h1 a { text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } h3 a { text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\r\n"
							+ "</style>\r\n" + "</head>\r\n"
							+ "<body style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;padding:0;Margin:0\">\r\n"
							+ "<div class=\"es-wrapper-color\" style=\"background-color:#F6F6F6\"><!--[if gte mso 9]>\r\n"
							+ "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n"
							+ "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\r\n" + "</v:background>\r\n"
							+ "<![endif]-->\r\n"
							+ "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#F6F6F6\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"Margin:0;padding-top:15px;padding-bottom:15px;padding-left:20px;padding-right:20px\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
							+ "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><img class=\"adapt-img\" src=\"https://edfnjh.stripocdn.email/content/guids/CABINET_2b3c9cb4a439f96bc49ae9dc931c0480/images/logo_padrao_png.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"270\"></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
							+ "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"right\" class=\"es-infoblock\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><a href=\"http://miklus.com.br/email01.html\" class=\"view\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\">Veja no browser</a></p></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table><!--[if mso]></td></tr></table><![endif]--></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td style=\"padding:0;Margin:0;padding-top:25px;background-color:#b5bcc4\" bgcolor=\"#b5bcc4\" align=\"left\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td class=\"es-m-txt-c\" align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#ffffff\"><br></h1></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:30px;padding-left:30px;padding-right:30px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:540px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:24px;color:#666666;font-size:16px\">Olá, O boleto nº "
							+ dup.getNumdup() + "-" + dup.getOrdem() + " do cliente " + dup.getDescricao()
							+ " no valor de R$ " + dup.getValor() + ", com emissao em&nbsp;</p></td>\r\n" + "</tr>\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td style=\"padding:0;Margin:0;padding-top:5px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px;text-align:justify\">"
							+ sdf.format(dup.getEmissao()) + " e vencimento em " + sdf.format(dup.getVencimento())
							+ " não foi emitido corretamente. Por favor verificar com urgência<br><br>Provável erro:<br>{"
							+ body.toString() + "}<br><br></p></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-left:30px;padding-right:30px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:540px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;font-size:0\">\r\n"
							+ "<table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td style=\"padding:0;Margin:0;border-bottom:1px solid #dadada;background:#FFFFFF none repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#9aaea6;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#9aaea6\" align=\"center\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td style=\"padding:20px;Margin:0;background-position:left top\" align=\"left\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
							+ "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td class=\"es-m-txt-с\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Contacte-nos</h3></td>\r\n"
							+ "</tr>\r\n" + "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0;font-size:0\">\r\n"
							+ "<table class=\"es-table-not-adapt es-social\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><a href=\"https://web.facebook.com/MiklusItSolutions/\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Miklus IT Solutions\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/facebook-circle-white.png\" alt=\"Fb\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><a href=\"https://www.linkedin.com/company/miklus-it-solutions\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Linkedin\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/linkedin-circle-white.png\" alt=\"In\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><a href=\"https://twitter.com/MiklusIT\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Twitter\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/twitter-circle-white.png\" alt=\"Tw\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0\"><a href=\"https://www.instagram.com/miklussolucoes/\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Instagram\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/instagram-circle-white.png\" alt=\"Ig\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
							+ "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Ou Ligue-nos</h3></td>\r\n"
							+ "</tr>\r\n" + "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td class=\"es-m-txt-с\" align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:30px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Goiânia - GO 62-3956-5500</h3><span style=\"color:#ffffff;font-size:20px\">Capitais - 4007-1054</span></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table><!--[if mso]></td></tr></table><![endif]--></td>\r\n" + "</tr>\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td style=\"padding:0;Margin:0;background-color:#f6f6f6\" bgcolor=\"#f6f6f6\" align=\"left\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td esdev-links-color=\"#666666\" align=\"center\" class=\"es-m-txt-с\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#666666;font-size:12px\">Copyright © 2023 - Miklus IT Solutions - Grupo Modernize</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#666666;font-size:12px\">Se não desejar mais receber nossos e-mail´s por favor responda este e-mail com o titulo Remover.<a target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px;line-height:18px\" class=\"unsubscribe\" href=\"\"></a></p></td>\r\n"
							+ "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
							+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;padding-bottom:30px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\r\n"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
							+ "<tr style=\"border-collapse:collapse\">\r\n"
							+ "<td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n"
							+ "</table></td>\r\n" + "</tr>\r\n" + "</table></td>\r\n" + "</tr>\r\n" + "</table>\r\n"
							+ "</div>\r\n" + "</body>\r\n" + "</html>",
					"text/html");

			// Send message
			Transport.send(message);

			EnviarWA enviarWA = new EnviarWA();
			//enviarWA.enviarMensagemWa(whatsApp);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void enviarConfirmacaoDeErroCadastroClientes(Clifor cli, String email, String whatsApp, String body) throws IOException {

		String to = "fiannceiro@miklus.com.br";

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.miklus.com.br";

		Properties props = new Properties();
		props.put("mail.smtp.user", "financeiro@miklus.com.br");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Boleto nao enviado GalaxPay");

			// Send the actual HTML message, as big as you like
			message.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"UTF-8\">\r\n"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\r\n"
					+ "<meta name=\"x-apple-disable-message-reformatting\">\r\n"
					+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
					+ "<meta content=\"telephone=no\" name=\"format-detection\">\r\n"
					+ "<title>Novo e-mail</title><!--[if (mso 16)]>\r\n"
					+ "<style type=\"text/css\">\r\n"
					+ "a {text-decoration: none;}\r\n"
					+ "</style>\r\n"
					+ "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\r\n"
					+ "<xml>\r\n"
					+ "<o:OfficeDocumentSettings>\r\n"
					+ "<o:AllowPNG></o:AllowPNG>\r\n"
					+ "<o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
					+ "</o:OfficeDocumentSettings>\r\n"
					+ "</xml>\r\n"
					+ "<![endif]-->\r\n"
					+ "<style type=\"text/css\">\r\n"
					+ ".rollover:hover .rollover-first {\r\n"
					+ "max-height:0px!important;\r\n"
					+ "display:none!important;\r\n"
					+ "}\r\n"
					+ ".rollover:hover .rollover-second {\r\n"
					+ "max-height:none!important;\r\n"
					+ "display:block!important;\r\n"
					+ "}\r\n"
					+ "#outlook a {\r\n"
					+ "padding:0;\r\n"
					+ "}\r\n"
					+ ".ExternalClass {\r\n"
					+ "width:100%;\r\n"
					+ "}\r\n"
					+ ".ExternalClass,\r\n"
					+ ".ExternalClass p,\r\n"
					+ ".ExternalClass span,\r\n"
					+ ".ExternalClass font,\r\n"
					+ ".ExternalClass td,\r\n"
					+ ".ExternalClass div {\r\n"
					+ "line-height:100%;\r\n"
					+ "}\r\n"
					+ ".es-button {\r\n"
					+ "mso-style-priority:100!important;\r\n"
					+ "text-decoration:none!important;\r\n"
					+ "}\r\n"
					+ "a[x-apple-data-detectors] {\r\n"
					+ "color:inherit!important;\r\n"
					+ "text-decoration:none!important;\r\n"
					+ "font-size:inherit!important;\r\n"
					+ "font-family:inherit!important;\r\n"
					+ "font-weight:inherit!important;\r\n"
					+ "line-height:inherit!important;\r\n"
					+ "}\r\n"
					+ ".es-desk-hidden {\r\n"
					+ "display:none;\r\n"
					+ "float:left;\r\n"
					+ "overflow:hidden;\r\n"
					+ "width:0;\r\n"
					+ "max-height:0;\r\n"
					+ "line-height:0;\r\n"
					+ "mso-hide:all;\r\n"
					+ "}\r\n"
					+ "[data-ogsb] .es-button {\r\n"
					+ "border-width:0!important;\r\n"
					+ "padding:10px 20px 10px 20px!important;\r\n"
					+ "}\r\n"
					+ "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:left } h2 { font-size:26px!important; text-align:left } h3 { font-size:20px!important; text-align:left } h1 a { text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } h3 a { text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\r\n"
					+ "</style>\r\n"
					+ "</head>\r\n"
					+ "<body style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;padding:0;Margin:0\">\r\n"
					+ "<div class=\"es-wrapper-color\" style=\"background-color:#F6F6F6\"><!--[if gte mso 9]>\r\n"
					+ "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n"
					+ "<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\r\n"
					+ "</v:background>\r\n"
					+ "<![endif]-->\r\n"
					+ "<table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#F6F6F6\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" style=\"padding:0;Margin:0\">\r\n"
					+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
					+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"Margin:0;padding-top:15px;padding-bottom:15px;padding-left:20px;padding-right:20px\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
					+ "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><img class=\"adapt-img\" src=\"https://edfnjh.stripocdn.email/content/guids/CABINET_2b3c9cb4a439f96bc49ae9dc931c0480/images/logo_padrao_png.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"270\"></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
					+ "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"right\" class=\"es-infoblock\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><a href=\"http://miklus.com.br/email01.html\" class=\"view\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#CCCCCC;font-size:12px\">Veja no browser</a></p></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table><!--[if mso]></td></tr></table><![endif]--></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table>\r\n"
					+ "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
					+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td style=\"padding:0;Margin:0;padding-top:25px;background-color:#b5bcc4\" bgcolor=\"#b5bcc4\" align=\"left\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td class=\"es-m-txt-c\" align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#ffffff\"><br></h1></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-top:30px;padding-left:30px;padding-right:30px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:540px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td style=\"padding:0;Margin:0;padding-top:5px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px;text-align:justify\"><br>Cadastro de clientes com erro:<br>o cliente: "+cli.getNom()+" nao foi cadastrado, favor conformar.<br>Provavel erro:<br>"+body.toString()+"<br><br><br></p></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"padding:0;Margin:0;padding-left:30px;padding-right:30px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:540px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;font-size:0\">\r\n"
					+ "<table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td style=\"padding:0;Margin:0;border-bottom:1px solid #dadada;background:#FFFFFF none repeat scroll 0% 0%;height:1px;width:100%;margin:0px\"></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table>\r\n"
					+ "<table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
					+ "<table class=\"es-footer-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#9aaea6;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#9aaea6\" align=\"center\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td style=\"padding:20px;Margin:0;background-position:left top\" align=\"left\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
					+ "<table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td class=\"es-m-txt-с\" align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Contacte-nos</h3></td>\r\n"
					+ "</tr>\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0;font-size:0\">\r\n"
					+ "<table class=\"es-table-not-adapt es-social\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><a href=\"https://web.facebook.com/MiklusItSolutions/\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Miklus IT Solutions\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/facebook-circle-white.png\" alt=\"Fb\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><a href=\"https://www.linkedin.com/company/miklus-it-solutions\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Linkedin\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/linkedin-circle-white.png\" alt=\"In\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;padding-right:10px\"><a href=\"https://twitter.com/MiklusIT\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Twitter\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/twitter-circle-white.png\" alt=\"Tw\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0\"><a href=\"https://www.instagram.com/miklussolucoes/\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px\"><img title=\"Instagram\" src=\"https://edfnjh.stripocdn.email/content/assets/img/social-icons/circle-white/instagram-circle-white.png\" alt=\"Ig\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\r\n"
					+ "<table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Ou Ligue-nos</h3></td>\r\n"
					+ "</tr>\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td class=\"es-m-txt-с\" align=\"center\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:10px\"><h3 style=\"Margin:0;line-height:30px;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#ffffff\">Goiânia - GO 62-3956-5500</h3><span style=\"color:#ffffff;font-size:20px\">Capitais - 4007-1054</span></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table><!--[if mso]></td></tr></table><![endif]--></td>\r\n"
					+ "</tr>\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td style=\"padding:0;Margin:0;background-color:#f6f6f6\" bgcolor=\"#f6f6f6\" align=\"left\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td esdev-links-color=\"#666666\" align=\"center\" class=\"es-m-txt-с\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:18px;color:#666666;font-size:12px\">Copyright © 2021&nbsp;- Miklus IT Solutions - Grupo Modernize<a target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#666666;font-size:12px;line-height:18px\" class=\"unsubscribe\" href=\"\"></a></p></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table>\r\n"
					+ "<table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0\">\r\n"
					+ "<table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;padding-bottom:30px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\r\n"
					+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\r\n"
					+ "<tr style=\"border-collapse:collapse\">\r\n"
					+ "<td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table></td>\r\n"
					+ "</tr>\r\n"
					+ "</table>\r\n"
					+ "</div>\r\n"
					+ "</body>\r\n"
					+ "</html>",
					"text/html");

			// Send message
			Transport.send(message);

			EnviarWA enviarWA = new EnviarWA();
			//enviarWA.enviarMensagemWaCadastroCliente(cli, whatsApp);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
