package com.ssafy.api.service;

import com.ssafy.api.request.MailDto;


import com.ssafy.api.request.UserEmailReq;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.entity.rdbms.User;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Override
    public MailDto makeLinkMail(UserEmailReq userEmailReq) throws MessagingException {
        User user = userRepository.findByUserEmail(userEmailReq.getUserEmail()).orElseThrow(NoSuchElementException::new);
        MailDto mailDto = MailDto.builder()
                .toAddress(userEmailReq.getUserEmail())
                .subject("email 인증 링크입니다.")
                .content("<div class=\"mail_view_contents\">\n" +
                        "  <div class=\"mail_view_contents_inner\" data-translate-body-17539=\"\">\n" +
                        "    <div>\n" +
                        "      <table style=\"\n" +
                        "          border-collapse: collapse;\n" +
                        "          table-layout: fixed;\n" +
                        "          border-spacing: 0;\n" +
                        "          mso-table-lspace: 0pt;\n" +
                        "          mso-table-rspace: 0pt;\n" +
                        "          vertical-align: top;\n" +
                        "          min-width: 320px;\n" +
                        "          margin: 0 auto;\n" +
                        "          background-color: #ffffff;\n" +
                        "          width: 100%;\n" +
                        "        \" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "        <tbody>\n" +
                        "          <tr style=\"vertical-align: top\">\n" +
                        "            <td style=\"\n" +
                        "                word-break: break-word;\n" +
                        "                border-collapse: collapse !important;\n" +
                        "                vertical-align: top;\n" +
                        "              \">\n" +
                        "              <div style=\"padding: 0px; background-color: transparent\">\n" +
                        "                <div style=\"\n" +
                        "                    margin: 0 auto;\n" +
                        "                    min-width: 320px;\n" +
                        "                    max-width: 500px;\n" +
                        "                    overflow-wrap: break-word;\n" +
                        "                    word-wrap: break-word;\n" +
                        "                    word-break: break-word;\n" +
                        "                    background-color: transparent;\n" +
                        "                  \">\n" +
                        "                  <div style=\"\n" +
                        "                      border-collapse: collapse;\n" +
                        "                      display: table;\n" +
                        "                      width: 100%;\n" +
                        "                      height: 100%;\n" +
                        "                      background-color: transparent;\n" +
                        "                    \">\n" +
                        "                     <div style=\"\n" +
                        "                        max-width: 320px;\n" +
                        "                        min-width: 500px;\n" +
                        "                        display: table-cell;\n" +
                        "                        vertical-align: top;\n" +
                        "                      \">\n" +
                        "                      <div style=\"height: 100%; width: 100% !important\">\n" +
                        "                        <div style=\"\n" +
                        "                            height: 100%;\n" +
                        "                            padding: 0px;\n" +
                        "                            border-width: 0px;\n" +
                        "                            border-style: solid;\n" +
                        "                            border-color: transparent;\n" +
                        "                            box-sizing: border-box;\n" +
                        "                          \">\n" +
                        "\n" +
                        "                          <table style=\"font-family: arial, helvetica, sans-serif\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                        "                            <tbody>\n" +
                        "                              <tr>\n" +
                        "                                <td align=\"left\" style=\"\n" +
                        "                                    overflow-wrap: break-word;\n" +
                        "                                    word-break: break-word;\n" +
                        "                                    padding: 50px 20px 40px;\n" +
                        "                                    font-family: arial, helvetica, sans-serif;\n" +
                        "                                  \">\n" +
                        "                                  <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                        "                                    <tbody>\n" +
                        "                                      <tr>\n" +
                        "                                        <td align=\"left\" style=\"\n" +
                        "                                            padding-right: 0px;\n" +
                        "                                            padding-left: 0px;\n" +
                        "                                          \">\n" +
                        "                                          <!-- <img align=\"left\" border=\"0\" src=\"\n" +
                        "\n" +
                        "                                            https://zigbang-cms.s3.ap-northeast-2.amazonaws.com/logo_zigbang_new_221201_64cc26c1e1.png\n" +
                        "                                            \n" +
                        "                                            \" alt=\"\" title=\"\" style=\"\n" +
                        "                                              outline: none;\n" +
                        "                                              text-decoration: none;\n" +
                        "                                              -ms-interpolation-mode: bicubic;\n" +
                        "                                              clear: both;\n" +
                        "                                              display: inline-block !important;\n" +
                        "                                              border: none;\n" +
                        "                                              height: auto;\n" +
                        "                                              float: none;\n" +
                        "                                              width: 34%;\n" +
                        "                                              max-width: 156.4px;\n" +
                        "                                            \" width=\"156.4\" loading=\"lazy\"> -->\n" +
                        "                                        </td>\n" +
                        "                                      </tr>\n" +
                        "                                    </tbody>\n" +
                        "                                  </table>\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                            </tbody>\n" +
                        "                          </table>\n" +
                        "\n" +
                        "                          <table style=\"font-family: arial, helvetica, sans-serif\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                        "                            <tbody>\n" +
                        "                              <tr>\n" +
                        "                                <td align=\"left\" style=\"\n" +
                        "                                    overflow-wrap: break-word;\n" +
                        "                                    word-break: break-word;\n" +
                        "                                    padding: 0px 20px 30px;\n" +
                        "                                    font-family: arial, helvetica, sans-serif;\n" +
                        "                                  \">\n" +
                        "                                  <h1 style=\"\n" +
                        "                                      margin: 0px;\n" +
                        "                                      line-height: 140%;\n" +
                        "                                      text-align: left;\n" +
                        "                                      word-wrap: break-word;\n" +
                        "                                      font-weight: normal;\n" +
                        "                                      font-family: arial, helvetica, sans-serif;\n" +
                        "                                      font-size: 28px;\n" +
                        "                                    \">\n" +
                        "                                    <strong>[Agora] 비밀번호 재설정</strong>\n" +
                        "                                  </h1>\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                            </tbody>\n" +
                        "                          </table>\n" +
                        "                        </div>\n" +
                        "                      </div>\n" +
                        "                    </div>\n" +
                        "                  </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "\n" +
                        "              <div style=\"padding: 0px; background-color: transparent\">\n" +
                        "                <div style=\"\n" +
                        "                    margin: 0 auto;\n" +
                        "                    min-width: 320px;\n" +
                        "                    max-width: 500px;\n" +
                        "                    overflow-wrap: break-word;\n" +
                        "                    word-wrap: break-word;\n" +
                        "                    word-break: break-word;\n" +
                        "                    background-color: transparent;\n" +
                        "                  \">\n" +
                        "                  <div style=\"\n" +
                        "                      border-collapse: collapse;\n" +
                        "                      display: table;\n" +
                        "                      width: 100%;\n" +
                        "                      height: 100%;\n" +
                        "                      background-color: transparent;\n" +
                        "                    \">\n" +
                        "                    <div style=\"\n" +
                        "                        max-width: 320px;\n" +
                        "                        min-width: 500px;\n" +
                        "                        display: table-cell;\n" +
                        "                        vertical-align: top;\n" +
                        "                      \">\n" +
                        "                      <div style=\"\n" +
                        "                          height: 100%;\n" +
                        "                          width: 100% !important;\n" +
                        "                          border-radius: 0px;\n" +
                        "                        \">\n" +
                        "                        <div style=\"\n" +
                        "                            height: 100%;\n" +
                        "                            padding: 0px;\n" +
                        "                            border-width: 0px;\n" +
                        "                            border-style: solid;\n" +
                        "                            border-color: transparent;\n" +
                        "                            border-radius: 0px;\n" +
                        "                            box-sizing: border-box;\n" +
                        "                          \">\n" +
                        "\n" +
                        "                          <table style=\"font-family: arial, helvetica, sans-serif\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                        "                            <tbody>\n" +
                        "                              <tr>\n" +
                        "                                <td align=\"left\" style=\"\n" +
                        "                                    overflow-wrap: break-word;\n" +
                        "                                    word-break: break-word;\n" +
                        "                                    padding: 10px 20px 50px;\n" +
                        "                                    font-family: arial, helvetica, sans-serif;\n" +
                        "                                  \">\n" +
                        "                                  <div style=\"\n" +
                        "                                      line-height: 140%;\n" +
                        "                                      text-align: left;\n" +
                        "                                      overflow-wrap: break-word;\n" +
                        "                                      height: 100%;\n" +
                        "                                    \">\n" +
                        "                                    <p style=\"\n" +
                        "                                        font-size: 14px;\n" +
                        "                                        line-height: 140%;\n" +
                        "                                        margin: 0px;\n" +
                        "                                      \">\n" +
                        "                                      <span style=\"\n" +
                        "                                          font-size: 16px;\n" +
                        "                                          line-height: 22.4px;\n" +
                        "                                        \">안녕하세요, "+ user.getName() +"고객님.</span>\n" +
                        "                                    </p>\n" +
                        "                                    <p style=\"\n" +
                        "                                        font-size: 14px;\n" +
                        "                                        line-height: 140%;\n" +
                        "                                        margin: 0px;\n" +
                        "                                      \">\n" +
                        "                                      <span style=\"\n" +
                        "                                          font-size: 16px;\n" +
                        "                                          line-height: 22.4px;\n" +
                        "                                        \">요청하신 비밀번호 재설정 링크를\n" +
                        "                                        알려드립니다.</span>\n" +
                        "                                    </p>\n" +
                        "                                    <p style=\"\n" +
                        "                                        font-size: 14px;\n" +
                        "                                        line-height: 140%;\n" +
                        "                                        margin: 0px;\n" +
                        "                                      \">\n" +
                        "                                      &nbsp;\n" +
                        "                                    </p>\n" +
                        "                                    <p style=\"\n" +
                        "                                        font-size: 14px;\n" +
                        "                                        line-height: 140%;\n" +
                        "                                        margin: 0px;\n" +
                        "                                      \">\n" +
                        "                                      <span style=\"\n" +
                        "                                          font-size: 16px;\n" +
                        "                                          line-height: 22.4px;\n" +
                        "                                        \">아래 버튼을 클릭하면 비밀번호 재설정 페이지로 연결됩니다.</span>\n" +
                        "                                    </p>\n" +
                        "                                  </div>\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                            </tbody>\n" +
                        "                          </table>\n" +
                        "\n" +
                        "            \n" +
                        "\n" +
                        "                          <table style=\"font-family: arial, helvetica, sans-serif\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                        "                            <tbody>\n" +
                        "                              <tr>\n" +
                        "                                <td align=\"left\" style=\"\n" +
                        "                                    overflow-wrap: break-word;\n" +
                        "                                    word-break: break-word;\n" +
                        "                                    padding: 0px 20px 20px;\n" +
                        "                                    font-family: arial, helvetica, sans-serif;\n" +
                        "                                  \">\n" +
                        "                                  <div align=\"center\" style=\"height: 100%\">\n" +
                        "                                    <a href=\"http://localhost:8080/change-password-email?token=" + jwtTokenUtil.createAccessToken(userEmailReq.getUserEmail()) + "\n" +
                        "                                    \"  style=\"\n" +
                        "                                        box-sizing: border-box;\n" +
                        "                                        display: inline-block;\n" +
                        "                                        text-decoration: none;\n" +
                        "                                        -webkit-text-size-adjust: none;\n" +
                        "                                        text-align: center;\n" +
                        "                                        color: #ffffff;\n" +
                        "                                        background-color: #ff6905;\n" +
                        "                                        border-radius: 4px;\n" +
                        "                                        -webkit-border-radius: 4px;\n" +
                        "                                        -moz-border-radius: 4px;\n" +
                        "                                        overflow-wrap: break-word;\n" +
                        "                                        word-break: break-word;\n" +
                        "                                        word-wrap: break-word;\n" +
                        "                                      \" rel=\"noreferrer noopener\">\n" +
                        "                                      <span style=\"\n" +
                        "                                          display: block;\n" +
                        "                                          padding: 10px 16px;\n" +
                        "                                          line-height: 120%;\n" +
                        "                                        \"><strong><span style=\"\n" +
                        "                                              font-size: 16px;\n" +
                        "                                              line-height: 19.2px;\n" +
                        "                                            \">비밀번호 재설정 하기 &gt;</span></strong></span>\n" +
                        "                                    </a>\n" +
                        "                                  </div>\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                            </tbody>\n" +
                        "                          </table>\n" +
                        "\n" +
                        "                          <table style=\"font-family: arial, helvetica, sans-serif\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                        "                            <tbody>\n" +
                        "                              <tr>\n" +
                        "                                <td align=\"left\" style=\"\n" +
                        "                                    overflow-wrap: break-word;\n" +
                        "                                    word-break: break-word;\n" +
                        "                                    padding: 10px 20px;\n" +
                        "                                    font-family: arial, helvetica, sans-serif;\n" +
                        "                                  \">\n" +
                        "                                  <table align=\"center\" height=\"0px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"\n" +
                        "                                      border-collapse: collapse;\n" +
                        "                                      table-layout: fixed;\n" +
                        "                                      border-spacing: 0;\n" +
                        "                                      mso-table-lspace: 0pt;\n" +
                        "                                      mso-table-rspace: 0pt;\n" +
                        "                                      vertical-align: top;\n" +
                        "                                      border-top: 1px solid #d7dee3;\n" +
                        "                                      -ms-text-size-adjust: 100%;\n" +
                        "                                      -webkit-text-size-adjust: 100%;\n" +
                        "                                    \">\n" +
                        "                                    <tbody>\n" +
                        "                                      <tr style=\"vertical-align: top\">\n" +
                        "                                        <td style=\"\n" +
                        "                                            word-break: break-word;\n" +
                        "                                            border-collapse: collapse !important;\n" +
                        "                                            vertical-align: top;\n" +
                        "                                            font-size: 0px;\n" +
                        "                                            line-height: 0px;\n" +
                        "                                            mso-line-height-rule: exactly;\n" +
                        "                                            -ms-text-size-adjust: 100%;\n" +
                        "                                            -webkit-text-size-adjust: 100%;\n" +
                        "                                          \">\n" +
                        "                                          <span>&nbsp;</span>\n" +
                        "                                        </td>\n" +
                        "                                      </tr>\n" +
                        "                                    </tbody>\n" +
                        "                                  </table>\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                            </tbody>\n" +
                        "                          </table>\n" +
                        "                        </div>\n" +
                        "                      </div>\n" +
                        "                    </div>\n" +
                        "                   </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "\n" +
                        "              <div style=\"padding: 0px; background-color: transparent\">\n" +
                        "                <div style=\"\n" +
                        "                    margin: 0 auto;\n" +
                        "                    min-width: 320px;\n" +
                        "                    max-width: 500px;\n" +
                        "                    overflow-wrap: break-word;\n" +
                        "                    word-wrap: break-word;\n" +
                        "                    word-break: break-word;\n" +
                        "                    background-color: transparent;\n" +
                        "                  \">\n" +
                        "                  <div style=\"\n" +
                        "                      border-collapse: collapse;\n" +
                        "                      display: table;\n" +
                        "                      width: 100%;\n" +
                        "                      height: 100%;\n" +
                        "                      background-color: transparent;\n" +
                        "                    \">\n" +
                        "                    <div style=\"\n" +
                        "                        max-width: 320px;\n" +
                        "                        min-width: 500px;\n" +
                        "                        display: table-cell;\n" +
                        "                        vertical-align: top;\n" +
                        "                      \">\n" +
                        "                      <div style=\"\n" +
                        "                          height: 100%;\n" +
                        "                          width: 100% !important;\n" +
                        "                          border-radius: 0px;\n" +
                        "                        \">\n" +
                        "                        <div style=\"\n" +
                        "                            height: 100%;\n" +
                        "                            padding: 0px;\n" +
                        "                            border-width: 0px;\n" +
                        "                            border-style: solid;\n" +
                        "                            border-color: transparent;\n" +
                        "                            border-radius: 0px;\n" +
                        "                            box-sizing: border-box;\n" +
                        "                          \">\n" +
                        "\n" +
                        "                          <table style=\"font-family: arial, helvetica, sans-serif\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                        "                            <tbody>\n" +
                        "                              <tr>\n" +
                        "                                <td align=\"left\" style=\"\n" +
                        "                                    overflow-wrap: break-word;\n" +
                        "                                    word-break: break-word;\n" +
                        "                                    padding: 10px 20px 20px;\n" +
                        "                                    font-family: arial, helvetica, sans-serif;\n" +
                        "                                  \">\n" +
                        "                                  <div style=\"\n" +
                        "                                      line-height: 140%;\n" +
                        "                                      text-align: left;\n" +
                        "                                      overflow-wrap: break-word;\n" +
                        "                                      height: 100%;\n" +
                        "                                    \">\n" +
                        "                                    <p style=\"\n" +
                        "                                        font-size: 14px;\n" +
                        "                                        line-height: 140%;\n" +
                        "                                        margin: 0px;\n" +
                        "                                      \">\n" +
                        "                                      <span style=\"\n" +
                        "                                          font-size: 16px;\n" +
                        "                                          line-height: 22.4px;\n" +
                        "                                        \">감사합니다.</span>\n" +
                        "                                    </p>\n" +
                        "                                    <p style=\"\n" +
                        "                                        font-size: 14px;\n" +
                        "                                        line-height: 140%;\n" +
                        "                                        margin: 0px;\n" +
                        "                                      \">\n" +
                        "                                      <span style=\"\n" +
                        "                                          font-size: 16px;\n" +
                        "                                          line-height: 22.4px;\n" +
                        "                                        \">Agora 고객센터 드림</span>\n" +
                        "                                    </p>\n" +
                        "                                  </div>\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                            </tbody>\n" +
                        "                          </table>\n" +
                        "                        </div>\n" +
                        "                      </div>\n" +
                        "                    </div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "        </tbody>\n" +
                        "      </table>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>").build();
        return mailDto;
    }

    @Override
    public void sendMail(MailDto mailDto) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(mailDto.getToAddress());
            helper.setSubject(mailDto.getSubject());
            helper.setText(mailDto.getContent(),true);

            javaMailSender.send(message);

    }
}
