package br.com.controlunion.iDocument.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.controlunion.iDocument.mail.EmailService;
import br.com.controlunion.iDocument.mail.Mail;
import br.com.controlunion.iDocument.model.Alarm;
import br.com.controlunion.iDocument.model.Document;
import br.com.controlunion.iDocument.model.Person;
import br.com.controlunion.iDocument.repository.AlarmRepository;
import br.com.controlunion.iDocument.repository.DocumentRepository;

@Service
public class DocumentSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentSchedule.class);

    @Value("${email.from}")
    String emailFrom;

    @Value("${email.to}")
    String emailTo;

    @Value("${cums.url}")
    String personUrl;

    @Inject
    DocumentRepository documentRepository;

    @Inject
    AlarmRepository alarmRepository;

    @Inject
    private EmailService emailService;

    @Scheduled(fixedRate=5000)
    public void checkDocument() {
        LOG.debug("DocumentSchedule is running!!!");

        List<Document> document = documentRepository.findValidity();

        if(document != null) {
            if(document.size() > 0) {

                for (Document dt : document) {

                    if(dt.getExpirationDate() != null) {
                        Date d = dt.getExpirationDate();
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 30);

                        Date today = new Date();
                        Calendar cToday = Calendar.getInstance();
                        cToday.setTime(today);

                        if(c.getTimeInMillis() > cToday.getTimeInMillis()) {
                            saveAlarm(dt);
                            sendEmail(dt);

                            LOG.debug("### DocumentSchedule was found!!! ###");
                        }
                    }

                    if(dt.getIssueDate() != null && dt.getValidity() != null) {
                        Date d1 = dt.getIssueDate();
                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(d1);
                        c1.add(Calendar.MONTH, dt.getValidity());

                        Date d2 = new Date();
                        Calendar c2 = Calendar.getInstance();
                        c2.setTime(d2);
                        c2.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH) - 30);

                        if(c1.getTimeInMillis() > c2.getTimeInMillis()) {
                            saveAlarm(dt);
                            sendEmail(dt);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                            LOG.debug(sdf.format(c1.getTime()) + " ::: @@@ DocumentSchedule was found!!! @@@ ::: " + sdf2.format(c2.getTime()));
                            break;
                        }
                    }

                }
            }
        }
    }

    private void saveAlarm(Document dt) {
        Alarm alarm = new Alarm();
        alarm.setDocument(dt);
        alarm.setAlarmDate(new Date());
        alarmRepository.save(alarm);
    }

    private void sendEmail(Document dt) {
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(personUrl, Person.class);

        Mail mail = new Mail();
        mail.setFrom(emailFrom);
        String[] to = {emailTo};
        mail.setTo(to);
        mail.setSubject("Documento Vencido");
        mail.setText("O documento " + dt.getTypeDocument().getName() + " do " + person.getName() + " irá expirar em menos de 30 dias.");

        try {
            emailService.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug("Error on send alert email");
        }
    }
}
