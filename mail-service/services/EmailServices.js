const Recipient = require("mailersend").Recipient;
const EmailParams = require("mailersend").EmailParams;
const MailerSend = require("mailersend").MailerSend;
const Sender = require("mailersend").Sender;

const sendEmail = async (data) => {

    const apiKey = process.env.EMAIL_API;
    const senderEmail = process.env.SENDER_EMAIL;

    const { email, redirectUrl, emailTemplateId } = data;
    const mailersend = new MailerSend({
        apiKey: apiKey,
    });

    const recipients = [new Recipient(email, "Recipient")];
    const sentFrom = new Sender(senderEmail, 'Ticket')

    const personalization = [
        {
            email: email,
            data: {
                redirectLink: redirectUrl,
            },
        }
    ];

    const emailParams = new EmailParams()
        .setFrom(sentFrom)
        .setTo(recipients)
        .setSubject("Thank you for sign in")
        .setTemplateId(emailTemplateId)
        .setPersonalization(personalization);

    await mailersend.email.send(emailParams)
}

module.exports = sendEmail;