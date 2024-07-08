const sendEmail = require("../services/EmailServices");
const emailTemplate = require("../constants/EmailTemplate");

exports.sendEmail = async (req, res) => {
    try {
        const { email, redirectUrl, template } = req.body;

        if (!email) {
            return res.status(400).json({
                message: "email is not provided",
            })
        }

        if (!template) {
            return res.status(400).json({
                message: "template is not provided",
            })
        }

        if (emailTemplate[template] == null || emailTemplate[template] == undefined) {
            return res.status(400).json({
                message: "template is undefined",
            })
        }

        let url = redirectUrl || "";

        const data = {
            email: email,
            redirectUrl: url,
            emailTemplateId: emailTemplate[template],
        }
        await sendEmail(data);

        res.status(200).json({
            message: "Email sent successfully",
        })
    } catch (error) {
        console.log(error);
        res.status(500).json({
            message: "Internal server error",
        })
    }
}