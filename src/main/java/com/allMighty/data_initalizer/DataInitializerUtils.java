package com.allMighty.data_initalizer;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
import com.allMighty.business_logic_domain.article.ArticleDTO;
import com.allMighty.business_logic_domain.event.EventDTO;
import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.medical_service.MedicalServiceDTO;
import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.enumeration.ImageContentType;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.core.io.ClassPathResource;

public class DataInitializerUtils {

  public static List<EventDTO> generateEvents() {
    // Event 1: Cardiological Innovations Seminar
    EventDTO event1 = new EventDTO();
    event1.setTitle("Seminari mbi Inovacionet Global në Kardiologji");
    event1.setPrice(250);
    event1.setContent(
        "<h1>Inovacionet në Kardiologji</h1>"
            + "<p>Ky seminar është mundësia juaj për të mësuar rreth teknikave të reja dhe inovative në shëndetin kardiovaskular. "
            + "Ekspertët ndërkombëtarë do të ndajnë njohuritë e tyre mbi përparimet më të fundit në diagnostikimin dhe trajtimin e sëmundjeve të zemrës. "
            + "Disa nga temat kryesore që do të diskutohen përfshijnë:</p>"
            + "<ul>"
            + "<li><b>Metodat më të reja të diagnostikimit:</b> Si të identifikoni problemet e zemrës me teknologjitë më të avancuara.</li>"
            + "<li><b>Terapitë inovative:</b> Shërbimet dhe trajtimet më të fundit për sëmundjet kardiovaskulare.</li>"
            + "<li><b>Shëndeti kardiovaskular dhe ushqimi:</b> Rëndësia e dietës dhe stërvitjes në ruajtjen e një zemre të shëndetshme.</li>"
            + "<li><b>Rëndësia e kontrollit të presionit të gjakut:</b> Si të menaxhojmë presionin e gjakut për të parandaluar sëmundjet e zemrës.</li>"
            + "<li><b>Shërbimet e reja për pacientët me insuficiencë kardiake:</b> Teknologjitë dhe trajtimet që mund të shpëtojnë jetë.</li>"
            + "</ul>"
            + "<p>Ky seminar është i hapur për të gjithë profesionistët e shëndetësisë që duan të rrisin njohuritë e tyre në fushën e kardiologjisë dhe të jenë pjesë e zhvillimeve më të fundit në këtë fushë.</p>");
    event1.setArchived(false);
    event1.setEventDate(LocalDateTime.of(2025, 6, 15, 10, 0)); // Data e ndryshme
    event1.setEventDuration(7200L); // Kohëzgjatja 2 orë
    event1.setLocation("Auditori i Kardiologjisë, Instituti Mjekësor");
    event1.setImages(getImageContent("event1.jpg"));
    event1.setOrganization("Limbach");
    event1.setGuestNumber(12);

    // Event 2: Dermatology Research Conference
    EventDTO event2 = new EventDTO();
    event2.setTitle("Konferenca e Kërkimeve në Dermatologji");
    event2.setPrice(200);
    event2.setContent(
        "<h1>Avancimet në Kërkimin Dermatologjik</h1>"
            + "<p>Ky event do të përqendrohet në teknologjitë më të fundit dhe metodologjitë e reja për trajtimin dhe diagnostikimin e sëmundjeve të lëkurës. "
            + "Ekspertë ndërkombëtarë do të diskutojnë rreth terapive dhe mundësive më të fundit për trajtimin e sëmundjeve dermatologjike. "
            + "Temat e diskutimit përfshijnë:</p>"
            + "<ul>"
            + "<li><b>Terapitë më të reja për aknet dhe sëmundjet e lëkurës:</b> Si të përmirësojmë shëndetin e lëkurës me teknologjitë e fundit.</li>"
            + "<li><b>Diagnostikimi i kancerit të lëkurës:</b> Metodat e reja për diagnostikimin e hershëm dhe trajtimin e kancerit të lëkurës.</li>"
            + "<li><b>Shkencat e lëkurës dhe bioteknologjia:</b> Rëndësia e përdorimit të bioteknologjisë në trajtimin e sëmundjeve të lëkurës.</li>"
            + "</ul>");
    event2.setArchived(false);
    event2.setEventDate(LocalDateTime.of(2025, 7, 20, 9, 0));
    event2.setEventDuration(5400L); // Kohëzgjatja 1.5 orë
    event2.setLocation("Salla e Konferencave të Dermatologjisë, Qendra Mjekësore");
    event2.setImages(getImageContent("event2.jpg"));
    event2.setOrganization("Albanian Dermatology Association");
    event2.setGuestNumber(20);

    // Event 3: Pediatric Medicine Workshop
    EventDTO event3 = new EventDTO();
    event3.setTitle("Punëtoria mbi Mjekësinë Pediatrike");
    event3.setPrice(150);
    event3.setContent(
        "<h1>Mjekësia Pediatrike: Avancimet dhe Sfidat</h1>"
            + "<p>Ky workshop është dedikuar mjekëve pediatër dhe specialistëve të shëndetit për të diskutuar metodat më të reja të trajtimit të sëmundjeve pediatrike. "
            + "Do të mbulohen tema të tilla si:</p>"
            + "<ul>"
            + "<li><b>Trajtimi i infeksioneve pediatrike:</b> Terapitë më të mira për fëmijët me sëmundje infektive.</li>"
            + "<li><b>Vaksinimi dhe imunizimi:</b> Pse vaksinat janë thelbësore për fëmijët dhe si të mbrojmë shëndetin e tyre.</li>"
            + "<li><b>Shëndeti mendor i fëmijëve:</b> Si të trajtojmë çrregullimet mendore tek fëmijët e vegjël.</li>"
            + "</ul>");
    event3.setArchived(false);
    event3.setEventDate(LocalDateTime.of(2025, 8, 10, 11, 0));
    event3.setEventDuration(7200L); // Kohëzgjatja 2 orë
    event3.setLocation("Qendra Shëndetësore e Pediatrisë");
    event3.setImages(getImageContent("event3.jpg"));
    event3.setOrganization("Albanian Pediatric Association");
    event3.setGuestNumber(15);

    // Event 4: Neurology Innovations Summit
    EventDTO event4 = new EventDTO();
    event4.setTitle("Samiti mbi Inovacionet në Neurologji");
    event4.setPrice(300);
    event4.setContent(
        "<h1>Inovacionet në Neurologji</h1>"
            + "<p>Ky samit mbledh profesionistë të neurologjisë dhe mjekët për të diskutuar rreth risive më të fundit në fushën e neurologjisë. "
            + "Në këtë event do të flitet rreth:</p>"
            + "<ul>"
            + "<li><b>Neurodegjenerimi dhe trajtimi i tij:</b> Metodat e reja për trajtimin e sëmundjeve të tilla si Alzheimer dhe Parkinson.</li>"
            + "<li><b>Studime mbi trurin dhe nervat:</b> Përparimet e fundit në hulumtimin e trurit dhe përpunimin e informacionit nervor.</li>"
            + "<li><b>Trajtimi i migrenës dhe dhimbjeve kronike:</b> Zgjidhje të reja për menaxhimin e dhimbjes neurologjike.</li>"
            + "</ul>");
    event4.setArchived(false);
    event4.setEventDate(LocalDateTime.of(2025, 9, 5, 14, 0));
    event4.setEventDuration(10800L); // Kohëzgjatja 3 orë
    event4.setLocation("Qendra e Neurologjisë");
    event4.setImages(getImageContent("event4.jpg"));
    event4.setOrganization("Albanian Neurology Association");
    event4.setGuestNumber(25);

    // Event 5: Gynecology and Obstetrics Forum
    EventDTO event5 = new EventDTO();
    event5.setTitle("Forum mbi Gjinekologjinë dhe Obstetrikën");
    event5.setPrice(200);
    event5.setContent(
        "<h1>Gjinekologjia dhe Obstetrika: Perspektiva të Reja</h1>"
            + "<p>Ky forum do të mbledhë profesionistë të gjinekologjisë dhe obstetrikës për të diskutuar mbi risitë më të fundit në këto dy fusha të mjekësisë. "
            + "Në forum do të përfshihen temat si:</p>"
            + "<ul>"
            + "<li><b>Menaxhimi i shtatzënisë:</b> Rëndësia e kujdesit prenatal dhe metodat më të reja të monitorimit të shtatzënisë.</li>"
            + "<li><b>Kirurgjia gjinekologjike minimale invazive:</b> Përfitimet dhe rreziqet e operacioneve pa nevojën për prerje të madhe.</li>"
            + "<li><b>Trajtimi i infertilitetit:</b> Metodat më të fundit të fertilizimit dhe trajtimit të çrregullimeve të fertilitetit.</li>"
            + "</ul>");
    event5.setArchived(false);
    event5.setEventDate(LocalDateTime.of(2025, 10, 10, 10, 0));
    event5.setEventDuration(7200L); // Kohëzgjatja 2 orë
    event5.setLocation("Salla e Gjinekologjisë, Spitali Universitar");
    event5.setImages(getImageContent("event5.jpg"));
    event5.setOrganization("Albanian Society of Gynecology and Obstetrics");
    event5.setGuestNumber(30);

    // Event 6: Oncological Advances Symposium
    EventDTO event6 = new EventDTO();
    event6.setTitle("Simpoziumi mbi Avancimet në Onkologji");
    event6.setPrice(350);
    event6.setContent(
        "<h1>Avancimet në Onkologji</h1>"
            + "<p>Ky simpozium ka për qëllim të prezantojë avancimet më të fundit në luftën kundër kancerit. "
            + "Ekspertët do të flasin rreth metodave të reja për diagnostikimin dhe trajtimin e kancerit të ndryshëm. Temat përfshijnë:</p>"
            + "<ul>"
            + "<li><b>Shërimi i kancerit të gjirit:</b> Terapi dhe operacione të reja për trajtimin e kancerit të gjirit.</li>"
            + "<li><b>Imunoterapia dhe trajtimi i kancerit:</b> Si mund të ndihmojë imuniteti në luftën kundër kancerit?</li>"
            + "<li><b>Avancimet në radioterapi:</b> Metodat e reja dhe më të sakta të përdorimit të rrezatimit për trajtimin e kancerit.</li>"
            + "</ul>");
    event6.setArchived(false);
    event6.setEventDate(LocalDateTime.of(2025, 11, 1, 9, 0));
    event6.setEventDuration(10800L); // Kohëzgjatja
    event6.setImages(getImageContent("event6.jpg"));
    event6.setOrganization("Albanian Society of Gynecology and Obstetrics");
    event6.setGuestNumber(30);

    return List.of(event1, event2, event3, event4, event5, event6);
  }

  public static List<ArticleDTO> generateArticles() {

    // Artikulli 1
    ArticleDTO article1 = new ArticleDTO();
    article1.setTitle("Kuptimi i Rolit të Aldosteronit në Hipertension");
    article1.setAuthor("Dr. Emily Williams");
    article1.setContent(
        "<h1>Aldosteroni dhe Hipertensioni</h1>"
            + "<p>Aldosteroni luan një rol kritik në rregullimin e presionit të gjakut. Në këtë artikull, do të shqyrtojmë se si nivelet e larta të aldosteronit kontribuojnë në zhvillimin e hipertensionit dhe ndikimin që ka ai në shëndetin kardiovaskular.</p>"
            + "<h2>Roli i Aldosteronit në Regullimin e Presionit të Gjakut</h2>"
            + "<p>Aldosteroni është një hormon i prodhuar nga gjëndra mbiveshkore që ndihmon në rregullimin e nivelit të natriumit dhe ujit në trup, duke pasuar rritjen e presionit të gjakut. Kur nivelet e tij janë të larta, mund të shkaktojnë mbajtje të ujit dhe rritje të presionit të gjakut, duke çuar në hipertension.</p>"
            + "<h2>Si Ndikon Aldosteroni në Shëndetin e Zemrës?</h2>"
            + "<ul>"
            + "<li><strong>Rritje të rrezikut për sulme në zemër dhe goditje në tru</strong></li>"
            + "<li><strong>Probleme me funksionin e veshkave</strong></li>"
            + "<li><strong>Ndryshime në strukturën e zemrës dhe dëmtim të enëve të gjakut</strong></li>"
            + "</ul>"
            + "<h2>Trajtimi i Hipertensionit të Shkaktuar nga Aldosteroni</h2>"
            + "<ul>"
            + "<li><strong>Medikamente anti-aldosteronike</strong> për të ulur ndikimin e aldosteronit në trup.</li>"
            + "<li><strong>Ndryshime në dietë dhe stilin e jetës</strong> për të përmirësuar shëndetin e zemrës dhe të enëve të gjakut.</li>"
            + "</ul>");
    article1.setArchived(false);
    article1.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Hipertensioni"))));
    article1.setSummary(
        "Ky artikull ofron një shqyrtim të thelluar të ndikimit të aldosteronit në hipertensionin dhe mënyrën si mund të menaxhohet ky gjendje.");
    article1.setCreationDate(LocalDateTime.of(2025, 6, 15, 10, 0));
    article1.setImages(getImageContent("article1.jpg"));

    // Artikulli 2
    ArticleDTO article2 = new ArticleDTO();
    article2.setTitle("Diabeti dhe Menaxhimi i Sheqerit në Gjak");
    article2.setAuthor("Dr. Marko Dashi");
    article2.setContent(
        "<h1>Diabeti dhe Menaxhimi i Sheqerit në Gjak</h1>"
            + "<p>Diabeti është një sëmundje kronike që ndikon në mënyrën si trupi përdor glukozën. Ky artikull do të shqyrtojë metodat për menaxhimin e niveleve të sheqerit në gjak dhe ndikimin e mundshëm të diabetit në shëndetin e përgjithshëm.</p>"
            + "<h2>Rreth Diabetit</h2>"
            + "<p>Diabeti tip 1 dhe 2 janë dy llojet më të zakonshme. Diabeti tip 1 është i lidhur me mungesën e insulinës, ndërsa diabeti tip 2 është i lidhur me rezistencën ndaj insulinës dhe zakonisht zhvillohet për shkak të stilit të jetesës së pasaktë.</p>"
            + "<h2>Metodat e Menaxhimit</h2>"
            + "<ul>"
            + "<li><strong>Monitorimi i përditshëm i niveleve të sheqerit në gjak</strong></li>"
            + "<li><strong>Mbajtja e një diete të shëndetshme dhe të balancuar</strong></li>"
            + "<li><strong>Ushtrimi fizik i rregullt për të përmirësuar përdorimin e glukozës në trup</strong></li>"
            + "</ul>");
    article2.setArchived(false);
    article2.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Diabeti"))));
    article2.setSummary(
        "Ky artikull ofron informacione të detajuara për menaxhimin e diabetit dhe ruajtjen e niveleve të shëndetshme të sheqerit në gjak.");
    article2.setCreationDate(LocalDateTime.of(2025, 6, 20, 10, 0));
    article2.setImages(getImageContent("article2.jpg"));

    // Artikulli 3
    ArticleDTO article3 = new ArticleDTO();
    article3.setTitle("Rëndësia e Ushtrimeve Fizike në Parandalimin e Sëmundjeve Kardiovaskulare");
    article3.setAuthor("Dr. Sara Lushi");
    article3.setContent(
        "<h1>Ushtrimi Fizik dhe Shëndeti Kardiovaskular</h1>"
            + "<p>Ushtrimi fizik është një nga mënyrat më të mira për të parandaluar sëmundjet kardiovaskulare. Ky artikull thekson rëndësinë e aktivitetit fizik dhe përfitimet e tij për zemrën dhe enët e gjakut.</p>"
            + "<h2>Përfitimet e Ushtrimeve Fizike</h2>"
            + "<ul>"
            + "<li><strong>Përmirëson qarkullimin e gjakut</strong></li>"
            + "<li><strong>Ndihmon në ruajtjen e presionit të gjakut në nivele të shëndetshme</strong></li>"
            + "<li><strong>Rrit kapacitetin e zemrës për të pompuar gjak</strong></li>"
            + "</ul>"
            + "<h2>Ushtrimet e Rekomanduara</h2>"
            + "<ul>"
            + "<li><strong>Vrapimi ose ecja e shpejtë</strong> për të rritur rrahjet e zemrës dhe për të stimuluar qarkullimin e gjakut.</li>"
            + "<li><strong>Noti</strong> është një ushtrim i shkëlqyer për shëndetin kardiovaskular.</li>"
            + "</ul>");
    article3.setArchived(false);
    article3.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Kardiologji"), new TagDTO("Ushtrimi Fizik"))));
    article3.setSummary(
        "Ky artikull shpjegon se si ushtrimet fizike ndihmojnë në parandalimin e sëmundjeve kardiovaskulare dhe përmirësojnë shëndetin e zemrës.");
    article3.setCreationDate(LocalDateTime.of(2025, 6, 25, 10, 0));
    article3.setImages(getImageContent("article1.jpg"));

    // Artikulli 4
    ArticleDTO article4 = new ArticleDTO();
    article4.setTitle("Trajtimi i Osteoporozës në Femra pas Menopauzës");
    article4.setAuthor("Dr. Elda Kryeziu");
    article4.setContent(
        "<h1>Osteoporoza dhe Menopauza</h1>"
            + "<p>Osteoporoza është një sëmundje që ndikon në densitetin e kockave dhe rrit rrezikun për thyerje. Ky artikull ofron informacion rreth trajtimit të osteoporozës tek gratë pas menopauzës.</p>"
            + "<h2>Rreziku i Osteoporozës pas Menopauzës</h2>"
            + "<p>Pasi gratë kalojnë nëpër menopauzë, ndodhin ndryshime hormonale që mund të çojnë në humbjen e densitetit të kockave dhe zhvillimin e osteoporozës.</p>"
            + "<h2>Metodat e Trajtimit</h2>"
            + "<ul>"
            + "<li><strong>Përmirësimi i dietës me kalcium dhe vitaminë D</strong></li>"
            + "<li><strong>Medikamente që ndihmojnë në forcimin e kockave</strong></li>"
            + "<li><strong>Ushtrime të rregullta për forcimin e kockave</strong></li>"
            + "</ul>");
    article4.setArchived(false);
    article4.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Osteoporoza"), new TagDTO("Menopauza"))));
    article4.setSummary(
        "Ky artikull ofron informacion të rëndësishëm për trajtimin e osteoporozës tek gratë pas menopauzës dhe si mund të parandaloni humbjen e densitetit të kockave.");
    article4.setCreationDate(LocalDateTime.of(2025, 7, 1, 10, 0));
    article4.setImages(getImageContent("article2.jpg"));

    // Artikulli 5
    ArticleDTO article5 = new ArticleDTO();
    article5.setTitle("Shëndeti Mental dhe Stresi në Jetën Moderne");
    article5.setAuthor("Dr. Leonora Çakaj");
    article5.setContent(
        "<h1>Stresi dhe Shëndeti Mental</h1>"
            + "<p>Stresi është një fenomen që preku jetën e të gjithëve. Ky artikull shqyrton ndikimin e stresit në shëndetin mendor dhe si mund ta menaxhojmë atë në jetën e përditshme.</p>"
            + "<h2>Përhapja e Stresit</h2>"
            + "<p>Stresi i vazhdueshëm mund të çojë në depresion, ankth dhe probleme të tjera psikologjike.</p>"
            + "<h2>Strategjitë për Menaxhimin e Stresit</h2>"
            + "<ul>"
            + "<li><strong>Ushtrimi i frymëmarrjes dhe meditimi</strong> për të ulur nivelet e stresit.</li>"
            + "<li><strong>Mbajtja e një diete të shëndetshme</strong> për të forcuar trurin dhe trupin.</li>"
            + "</ul>");
    article5.setArchived(false);
    article5.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Shëndeti Mental"), new TagDTO("Stresi"))));
    article5.setSummary(
        "Ky artikull ofron këshilla dhe strategji për menaxhimin e stresit dhe përmirësimin e shëndetit mendor.");
    article5.setCreationDate(LocalDateTime.of(2025, 7, 5, 10, 0));
    article5.setImages(getImageContent("article1.jpg"));

    // Artikulli 6
    ArticleDTO article6 = new ArticleDTO();
    article6.setTitle("Parandalimi i Sëmundjeve Infektive në Rrethana Spitalore");
    article6.setAuthor("Dr. Artiola Hoxha");
    article6.setContent(
        "<h1>Parandalimi i Sëmundjeve Infektive në Spital</h1>"
            + "<p>Shëndeti i pacientëve mund të jetë në rrezik për shkak të infeksioneve që mund të përhapen në mjedisin spitalor. Ky artikull shqyrton metodat e parandalimit të këtyre infeksioneve.</p>"
            + "<h2>Masat e Parandalimit</h2>"
            + "<ul>"
            + "<li><strong>Ndjekja e standardeve të higjienës personale dhe spitalore</strong></li>"
            + "<li><strong>Izolimi i pacientëve të prekur nga infeksionet e rrezikshme</strong></li>"
            + "<li><strong>Trajtimi i shpejtë dhe efektiv i infeksioneve të mundshme</strong></li>"
            + "</ul>");
    article6.setArchived(false);
    article6.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Infeksione"), new TagDTO("Spitalet"))));
    article6.setSummary(
        "Ky artikull përshkruan masat e nevojshme për parandalimin e infeksioneve në mjedisin spitalor dhe sigurinë e pacientëve.");
    article6.setCreationDate(LocalDateTime.of(2025, 7, 10, 10, 0));
    article6.setImages(getImageContent("article2.jpg"));

    // Artikulli 7
    ArticleDTO article7 = new ArticleDTO();
    article7.setTitle("Rëndësia e Kontrollit të Rregullt Shëndetësor për Njerëzit e Moshuar");
    article7.setAuthor("Dr. Gerti Shkëmbi");
    article7.setContent(
        "<h1>Kontrolli i Rregullt Shëndetësor</h1>"
            + "<p>Kontrolli i rregullt i shëndetit është thelbësor për të ruajtur mirëqenien e të moshuarve. Ky artikull tregon se si mund të përmirësojmë cilësinë e jetës në moshën e tretë përmes kontrollimeve të shëndetit të rregullta.</p>"
            + "<h2>Kontrolli i Shëndetit për Të Moshuarit</h2>"
            + "<ul>"
            + "<li><strong>Kontrolli i tensionit të gjakut dhe niveleve të sheqerit</strong></li>"
            + "<li><strong>Kontrolli i shëndetit të zemrës dhe të enëve të gjakut</strong></li>"
            + "<li><strong>Kontrolli i shëndetit mendor për të parandaluar depresionin dhe demencën</strong></li>"
            + "</ul>");
    article7.setArchived(false);
    article7.setTags(
        new HashSet<>(
            Arrays.asList(
                new TagDTO("Shëndeti i Të Moshuarve"), new TagDTO("Kontrolli i Shëndetit"))));
    article7.setSummary(
        "Ky artikull ndihmon të moshuarit të kuptojnë rëndësinë e kontrollit të rregullt të shëndetit dhe si të ruajnë mirëqenien e tyre.");
    article7.setCreationDate(LocalDateTime.of(2025, 7, 15, 10, 0));
    article7.setImages(getImageContent("article1.jpg"));

    return List.of(article1, article2, article3, article4, article5, article6, article7);
  }

  public static List<AnalysisDTO> generateAnalysis() {
    // Analiza e Aldosteronit
    AnalysisDTO aldosteroneAnalysis = new AnalysisDTO();
    aldosteroneAnalysis.setMedicalName("Aldosteroni");
    aldosteroneAnalysis.setSynonym("Nuk ka");
    aldosteroneAnalysis.setPrice(150);
    aldosteroneAnalysis.setArchived(false);
    aldosteroneAnalysis.setRemoved(false);
    aldosteroneAnalysis.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Hipertensioni"))));
    List<AnalysisDetailDTO> detailDTOS = new ArrayList<>();
    detailDTOS.add(new AnalysisDetailDTO("Kategoria", "Endokrinologji"));
    detailDTOS.add(new AnalysisDetailDTO("Mostra", "Plasma EDTA"));
    detailDTOS.add(new AnalysisDetailDTO("Stabiliteti", "E ngrirë: 4 muaj"));
    detailDTOS.add(
        new AnalysisDetailDTO(
            "Preanalitika",
            "Në përgatitje për marrjen e gjakut, spironolaktoni, eplerenoni, kontraceptivët që përmbajnë drospirenonë, triamtereni dhe amiloridi duhet të ndërpriten 4 javë para testimit, pasi ato kanë një efekt të zgjatur mbi ARQ. Gjatë kësaj kohe, duhet të shmanget edhe konsumimi i jamballit dhe duahnit që përtypet. Antihipertensivët e 'lejuar' përfshijnë kryesisht alfa-bllokuesit periferikë (p.sh., doksazosina) dhe antagonistët e kalciumit të tipit jo-dihidropiridin (p.sh., verapamili). Përveç kësaj, para çdo matjeje, nivelet e kaliumit duhet të jenë të normalizuara dhe pacienti duhet të inkurajohet të ndjekë një dietë të ekuilibruar në lidhje me marrjen e kripës. Mostra merret në mëngjes, afërsisht dy orë pas zgjimit, në një pozicion ulur pas 5 deri në 15 minuta pushim."));
    detailDTOS.add(new AnalysisDetailDTO("Metoda", "CLIA: Chemilumineszenz assay"));
    detailDTOS.add(
        new AnalysisDetailDTO(
            "Indikacioni klinik",
            "Diagnoza dhe progresioni i hiperaldosteronizmit ose hipoaldosteronizmit; incidentaloma. Nëse dyshohet për hiperaldosteronizëm, rekomandohet vlerësimi i raportit të aldosteron-it ndaj reninës, kuocienti aldosteron-reninë (ARQ), duhet të përcaktohet fillimisht në gjak."));
    detailDTOS.add(
        new AnalysisDetailDTO(
            "Interpretimi i rezultatit",
            "E rritur: Hiperaldosteronizmi primar = Sindroma Conn (adenoma e NNR prodhuese e aldosteronit, kanceri prodhuese i aldosteronit, hiperaldosteronizmi idiopatik, hiperaldosteronizmi i ndjeshëm ndaj glukokortikoideve), hiperaldosteronizmi sekondar (stenoza e arteries renale, tumoret prodhuese të reninës, hipertensioni esencial, insuficienca renale kronike, edema, asciti), shtatzënia, panarteriti nodoz, sindroma Bartter, sindroma pseudo-Bartter, postoperativ, ndonjëherë sindroma Cushing dhe sindroma adrenogjenitale. E ulur: Hipoaldosteronizmi primar në kuadër të sëmundjes Addison, hipoaldosteronizmi sekondar (hiporeninemia, insuficienca e hipofizës), defekte idiopatike dhe të izoluara në biosintezën e aldosteronit."));
    detailDTOS.add(new AnalysisDetailDTO("Akredituar nga ISO 15189", "Po"));
    aldosteroneAnalysis.setDetails(detailDTOS);

    // Analiza e Reninës
    AnalysisDTO reninAnalysis = new AnalysisDTO();
    reninAnalysis.setMedicalName("Renina");
    reninAnalysis.setSynonym("Renina Plazmatike");
    reninAnalysis.setPrice(130);
    reninAnalysis.setArchived(false);
    reninAnalysis.setRemoved(false);
    reninAnalysis.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Hipertensioni"))));

    List<AnalysisDetailDTO> reninDetails = new ArrayList<>();
    reninDetails.add(new AnalysisDetailDTO("Kategoria", "Endokrinologji"));
    reninDetails.add(new AnalysisDetailDTO("Mostra", "Plasma EDTA"));
    reninDetails.add(new AnalysisDetailDTO("Stabiliteti", "E ngrirë: 6 muaj"));
    reninDetails.add(
        new AnalysisDetailDTO(
            "Preanalitika",
            "Para testit, pacienti duhet të shmangë përdorimin e medikamenteve që ndikojnë në nivelet e reninës, siç janë betabllokuesit, ACE inhibitorët, dhe angiotenzin II receptor antagonistët."));
    reninDetails.add(new AnalysisDetailDTO("Metoda", "CLIA: Chemilumineszenz assay"));
    reninDetails.add(
        new AnalysisDetailDTO(
            "Indikacioni klinik",
            "Vlerësimi i raportit aldosteron-reninë, për diagnozën e hipertensionit primar dhe sekundar, si dhe sëmundjeve të ndryshme endokrine."));
    reninDetails.add(
        new AnalysisDetailDTO(
            "Interpretimi i rezultatit",
            "Raporti i reninës mund të jetë i ulët në rastin e hipertensionit sekondar dhe të lartë në hipertensionin primar."));
    reninDetails.add(new AnalysisDetailDTO("Akredituar nga ISO 15189", "Po"));
    reninAnalysis.setDetails(reninDetails);

    // Analiza e Testosteronit
    AnalysisDTO testosteroneAnalysis = new AnalysisDTO();
    testosteroneAnalysis.setMedicalName("Testosteroni");
    testosteroneAnalysis.setSynonym("Androgjen");
    testosteroneAnalysis.setPrice(200);
    testosteroneAnalysis.setArchived(false);
    testosteroneAnalysis.setRemoved(false);
    testosteroneAnalysis.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Hormonale"))));

    List<AnalysisDetailDTO> testosteroneDetails = new ArrayList<>();
    testosteroneDetails.add(new AnalysisDetailDTO("Kategoria", "Endokrinologji"));
    testosteroneDetails.add(new AnalysisDetailDTO("Mostra", "Serum"));
    testosteroneDetails.add(new AnalysisDetailDTO("Stabiliteti", "E ngrirë: 12 muaj"));
    testosteroneDetails.add(
        new AnalysisDetailDTO(
            "Preanalitika",
            "Duhet të shmanget përdorimi i medikamenteve që mund të ndikojnë në nivelet e testosteronit, siç janë kortikosteroidet. Testi bëhet në mëngjes, pas një periudhe pushimi nga aktivitetet fizike."));
    testosteroneDetails.add(new AnalysisDetailDTO("Metoda", "CLIA: Chemilumineszenz assay"));
    testosteroneDetails.add(
        new AnalysisDetailDTO(
            "Indikacioni klinik",
            "Vlerësimi i niveleve të testosteronit për diagnozën e hipogonadizmit, infertilitetit, dhe shqetësimeve të tjera hormonale."));
    testosteroneDetails.add(
        new AnalysisDetailDTO(
            "Interpretimi i rezultatit",
            "Nivelet e ulëta të testosteronit mund të tregojnë hipogonadizëm, ndërsa nivelet e larta mund të jenë të lidhura me tumore të testikujve apo abuzim me steroide."));
    testosteroneDetails.add(new AnalysisDetailDTO("Akredituar nga ISO 15189", "Po"));
    testosteroneAnalysis.setDetails(testosteroneDetails);

    // Analiza e Estradiolit
    AnalysisDTO estradiolAnalysis = new AnalysisDTO();
    estradiolAnalysis.setMedicalName("Estradioli");
    estradiolAnalysis.setSynonym("E2");
    estradiolAnalysis.setPrice(180);
    estradiolAnalysis.setArchived(false);
    estradiolAnalysis.setRemoved(false);
    estradiolAnalysis.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Hormonal"))));

    List<AnalysisDetailDTO> estradiolDetails = new ArrayList<>();
    estradiolDetails.add(new AnalysisDetailDTO("Kategoria", "Endokrinologji"));
    estradiolDetails.add(new AnalysisDetailDTO("Mostra", "Serum"));
    estradiolDetails.add(new AnalysisDetailDTO("Stabiliteti", "E ngrirë: 12 muaj"));
    estradiolDetails.add(
        new AnalysisDetailDTO(
            "Preanalitika",
            "Testi duhet të kryhet në fazën luteale të ciklit menstrual për gratë, dhe në periudhën e pasmenopauzës për gratë që janë pas menopauzës."));
    estradiolDetails.add(new AnalysisDetailDTO("Metoda", "CLIA: Chemilumineszenz assay"));
    estradiolDetails.add(
        new AnalysisDetailDTO(
            "Indikacioni klinik",
            "Përdoret për vlerësimin e funksionit ovarial, diagnozën e sindromës policistike të vezoreve, dhe shqetësime të tjera hormonale."));
    estradiolDetails.add(
        new AnalysisDetailDTO(
            "Interpretimi i rezultatit",
            "Estradioli i ulët mund të tregojë çrregullime hormonale si sindroma policistike e vezoreve (PCOS), ndërsa nivelet e larta mund të lidhen me tumore ose trajtime hormonale."));
    estradiolDetails.add(new AnalysisDetailDTO("Akredituar nga ISO 15189", "Po"));
    estradiolAnalysis.setDetails(estradiolDetails);

    // Analiza e Prolaktinës
    AnalysisDTO prolactinAnalysis = new AnalysisDTO();
    prolactinAnalysis.setMedicalName("Prolaktina");
    prolactinAnalysis.setSynonym("Lactotropin");
    prolactinAnalysis.setPrice(170);
    prolactinAnalysis.setArchived(false);
    prolactinAnalysis.setRemoved(false);
    prolactinAnalysis.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Hormonale"))));

    List<AnalysisDetailDTO> prolactinDetails = new ArrayList<>();
    prolactinDetails.add(new AnalysisDetailDTO("Kategoria", "Endokrinologji"));
    prolactinDetails.add(new AnalysisDetailDTO("Mostra", "Serum"));
    prolactinDetails.add(new AnalysisDetailDTO("Stabiliteti", "E ngrirë: 6 muaj"));
    prolactinDetails.add(
        new AnalysisDetailDTO(
            "Preanalitika",
            "Duhet të shmanget përdorimi i medikamenteve që ndikojnë në nivelet e prolaktinës, siç janë antipsikotikët dhe medikamentet e tjera që ndikojnë në sistemin dopaminergik."));
    prolactinDetails.add(new AnalysisDetailDTO("Metoda", "CLIA: Chemilumineszenz assay"));
    prolactinDetails.add(
        new AnalysisDetailDTO(
            "Indikacioni klinik",
            "Vlerësimi i prolaktinës për diagnozën e hiperprolaktinemisë, çrregullimeve të menstruacioneve dhe infertilitetit."));
    prolactinDetails.add(
        new AnalysisDetailDTO(
            "Interpretimi i rezultatit",
            "Nivelet e larta mund të tregojnë prolaktinoma, çrregullime hipofizare, ose përdorim të medikamenteve që ndikojnë në prolaktinë."));
    prolactinDetails.add(new AnalysisDetailDTO("Akredituar nga ISO 15189", "Po"));
    prolactinAnalysis.setDetails(prolactinDetails);

    // Analiza e Kortizolit
    AnalysisDTO cortisolAnalysis = new AnalysisDTO();
    cortisolAnalysis.setMedicalName("Kortizoli");
    cortisolAnalysis.setSynonym("Hidrokortizon");
    cortisolAnalysis.setPrice(160);
    cortisolAnalysis.setArchived(false);
    cortisolAnalysis.setRemoved(false);
    cortisolAnalysis.setTags(
        new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Stresi"))));

    List<AnalysisDetailDTO> cortisolDetails = new ArrayList<>();
    cortisolDetails.add(new AnalysisDetailDTO("Kategoria", "Endokrinologji"));
    cortisolDetails.add(new AnalysisDetailDTO("Mostra", "Plasma EDTA"));
    cortisolDetails.add(new AnalysisDetailDTO("Stabiliteti", "E ngrirë: 3 muaj"));
    cortisolDetails.add(
        new AnalysisDetailDTO(
            "Preanalitika",
            "Përpara testit, pacientët duhet të shmangin përdorimin e medikamenteve që mund të ndikojnë në nivelet e kortizolit, siç janë glukokortikoidet."));
    cortisolDetails.add(new AnalysisDetailDTO("Metoda", "CLIA: Chemilumineszenz assay"));
    cortisolDetails.add(
        new AnalysisDetailDTO(
            "Indikacioni klinik",
            "Vlerësimi i niveleve të kortizolit për diagnostikimin e sëmundjeve të gjëndrës mbiveshkore dhe hipofizës."));
    cortisolDetails.add(
        new AnalysisDetailDTO(
            "Interpretimi i rezultatit",
            "Nivelet e larta mund të tregojnë sëmundje të gjëndrës mbiveshkore si sindroma Cushing, ndërsa nivelet e ulëta mund të tregojnë insuficiencë adrenal."));
    cortisolDetails.add(new AnalysisDetailDTO("Akredituar nga ISO 15189", "Po"));
    cortisolAnalysis.setDetails(cortisolDetails);

    return List.of(
        aldosteroneAnalysis,
        reninAnalysis,
        testosteroneAnalysis,
        estradiolAnalysis,
        prolactinAnalysis,
        cortisolAnalysis);
  }

  public static List<MedicalServiceDTO> generateMedicalServices(){
    MedicalServiceDTO service1 = new MedicalServiceDTO();
    service1.setTitle("Konsulta Endokrinologjike");
    service1.setShowInHomePage(true);
    service1.setArchived(false);
    service1.setRemoved(false);
    service1.setContent(
            "Konsulta Endokrinologjike ofron një shërbim të plotë për pacientët që vuajnë nga çrregullime hormonale dhe metabolike. Specialistët tanë ofrojnë diagnoza dhe trajtim për sëmundje të tilla si: \n" +
                    "- Hipertensioni dhe problemet me tensionin e lartë\n" +
                    "- Diabeti dhe çrregullimet e nivelit të sheqerit në gjak\n" +
                    "- Çrregullimet e tiroideve dhe probleme të tjera hormonale\n" +
                    "Konsulta është e përshtatshme për pacientët që kanë nevojë për vlerësim dhe trajtim të çrregullimeve të sistemit endokrin.");
    service1.setAnalysisIds(Arrays.asList(1L, 2L));
    service1.setArticleIds(Arrays.asList(1L, 2L));
    service1.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Endokrinologji"), new TagDTO("Çrregullime Hormonal"))));
    service1.setImages(getImageContent("medical1.jpg"));

// Shërbimi Mjekësor 2: Konsulta Kardiologjike
    MedicalServiceDTO service2 = new MedicalServiceDTO();
    service2.setTitle("Konsulta Kardiologjike");
    service2.setShowInHomePage(true);
    service2.setArchived(false);
    service2.setRemoved(false);
    service2.setContent(
            "Konsulta Kardiologjike ofron vlerësim dhe trajtim për pacientët që vuajnë nga probleme kardiovaskulare, siç janë:\n" +
                    "- Hipertensioni i lartë\n" +
                    "- Sëmundjet koronare të zemrës\n" +
                    "- Çrregullimet e ritmit të zemrës\n" +
                    "- Dhembje gjoksi dhe probleme të tjera kardiake");
    service2.setAnalysisIds(Arrays.asList(2L, 3L)); // Kombinim i analizave të ndryshme
    service2.setArticleIds(Arrays.asList(1L, 3L)); // Kombinim i artikujve të ndryshëm
    service2.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Kardiologji"), new TagDTO("Sëmundjet e Zemrës"))));
    service2.setImages(getImageContent("medical2.jpg"));

// Shërbimi Mjekësor 3: Konsulta Ginekolologjike
    MedicalServiceDTO service3 = new MedicalServiceDTO();
    service3.setTitle("Konsulta Ginekolologjike");
    service3.setShowInHomePage(true);
    service3.setArchived(false);
    service3.setRemoved(false);
    service3.setContent(
            "Konsulta Ginekolologjike ofron shërbim të plotë për problemet ginekolgjike, përfshirë:\n" +
                    "- Kontrollin rutinë ginekolgjik\n" +
                    "- Diagnostikimin dhe trajtimin e çrregullimeve hormonale\n" +
                    "- Trajtimin e infeksioneve të organeve të lindjes\n" +
                    "- Menaxhimi i shtatzënisë dhe lindjes");
    service3.setAnalysisIds(Arrays.asList(4L, 5L)); // Kombinim i analizave të ndryshme
    service3.setArticleIds(Arrays.asList(2L, 4L)); // Kombinim i artikujve të ndryshëm
    service3.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Ginekolgji"), new TagDTO("Shtatzënia"))));
    service3.setImages(getImageContent("medical3jpg"));

// Shërbimi Mjekësor 4: Konsulta Gastroenterologjike
    MedicalServiceDTO service4 = new MedicalServiceDTO();
    service4.setTitle("Konsulta Gastroenterologjike");
    service4.setShowInHomePage(true);
    service4.setArchived(false);
    service4.setRemoved(false);
    service4.setContent(
            "Konsulta Gastroenterologjike ofron shërbim për pacientët që vuajnë nga çrregullime të sistemit tretës, siç janë:\n" +
                    "- Dhimbjet e barkut\n" +
                    "- Probleme me tretjen dhe aciditetin\n" +
                    "- Çrregullime të mëlçisë dhe pankreasit\n" +
                    "- Koliti dhe ulçera");
    service4.setAnalysisIds(Arrays.asList(5L, 6L)); // Kombinim i analizave të ndryshme
    service4.setArticleIds(Arrays.asList(1L, 5L)); // Kombinim i artikujve të ndryshëm
    service4.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Gastroenterologji"), new TagDTO("Sistemi Tretës"))));
    service4.setImages(getImageContent("medical4.jpg"));

// Shërbimi Mjekësor 5: Konsulta Dermatologjike
    MedicalServiceDTO service5 = new MedicalServiceDTO();
    service5.setTitle("Konsulta Dermatologjike");
    service5.setShowInHomePage(true);
    service5.setArchived(false);
    service5.setRemoved(false);
    service5.setContent(
            "Konsulta Dermatologjike ofron diagnostikimin dhe trajtimin e problemeve të lëkurës, duke përfshirë:\n" +
                    "- Aknet dhe çrregullimet e tjera të lëkurës\n" +
                    "- Infeksionet e lëkurës\n" +
                    "- Sëmundjet autoimune të lëkurës\n" +
                    "- Trajtimi i shenjave të plakjes dhe radhët e lëkurës");
    service5.setAnalysisIds(Arrays.asList(2L, 6L)); // Kombinim i analizave të ndryshme
    service5.setArticleIds(Arrays.asList(3L, 6L)); // Kombinim i artikujve të ndryshëm
    service5.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Dermatologji"), new TagDTO("Probleme të Lëkurës"))));
    service5.setImages(getImageContent("medical5.jpg"));

// Shërbimi Mjekësor 6: Konsulta Ortopedike
    MedicalServiceDTO service6 = new MedicalServiceDTO();
    service6.setTitle("Konsulta Ortopedike");
    service6.setShowInHomePage(true);
    service6.setArchived(false);
    service6.setRemoved(false);
    service6.setContent(
            "Konsulta Ortopedike ofron diagnostikimin dhe trajtimin e problemeve muskulore dhe të kockave, duke përfshirë:\n" +
                    "- Dhembjet e kyçeve dhe muskujve\n" +
                    "- Frakturat dhe dëmtimet e kockave\n" +
                    "- Çrregullimet e shpinës\n" +
                    "- Korrigjimi i deformimeve muskulore");
    service6.setAnalysisIds(Arrays.asList(3L, 4L)); // Kombinim i analizave të ndryshme
    service6.setArticleIds(Arrays.asList(2L, 7L)); // Kombinim i artikujve të ndryshëm
    service6.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Ortopedi"), new TagDTO("Trajtimi i Kockave"))));
    service6.setImages(getImageContent("medical6.jpg"));

// Shërbimi Mjekësor 7: Konsulta Neurologjike
    MedicalServiceDTO service7 = new MedicalServiceDTO();
    service7.setTitle("Konsulta Neurologjike");
    service7.setShowInHomePage(true);
    service7.setArchived(false);
    service7.setRemoved(false);
    service7.setContent(
            "Konsulta Neurologjike ofron diagnostikimin dhe trajtimin e problemeve të sistemit nervor, përfshirë:\n" +
                    "- Migrena dhe dhimbjet e kokës\n" +
                    "- Sëmundjet neurodegjenerative si Alzheimer dhe Parkinson\n" +
                    "- Çrregullimet e gjumit\n" +
                    "- Dëmtimet e trurit dhe palcës kurrizore");
    service7.setAnalysisIds(Arrays.asList(1L, 5L)); // Kombinim i analizave të ndryshme
    service7.setArticleIds(Arrays.asList(4L, 7L)); // Kombinim i artikujve të ndryshëm
    service7.setTags(
            new HashSet<>(Arrays.asList(new TagDTO("Neurologji"), new TagDTO("Sistemi Nervor"))));
    service7.setImages(getImageContent("medical7.jpg"));
    return List.of(
            service1,
            service2,
            service3,
            service4,
            service5,
            service6,
            service7
    );


  }


  private static List<ImageDTO> getImageContent(String fileName) {
    ImageDTO imageDTO = new ImageDTO();
    imageDTO.setImageContentType(ImageContentType.JPEG);
    imageDTO.setImageData(getImageByteContent(fileName));
    return Collections.singletonList(imageDTO);
  }

  private static String getImageByteContent(String fileName) {
    try {
      ClassPathResource resource = new ClassPathResource("images/" + fileName);
      try (InputStream inputStream = resource.getInputStream()) {
        byte[] imageBytes = inputStream.readAllBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
      }
    } catch (IOException e) {
      return null;
    }
  }
}
