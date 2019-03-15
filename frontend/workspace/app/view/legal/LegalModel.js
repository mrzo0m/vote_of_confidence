/**
 * Created by olegbursinov on 2019-02-24.
 */
Ext.define('VocApp.view.legal.LegalModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.legal',

    stores: {
        /*
        A declaration of Ext.data.Store configurations that are first processed as binds to produce an effective
        store configuration. For example:

        users: {
            model: 'Legal',
            autoLoad: true
        }
        */
    },

    data: {
        name: 'VocApp',
        legal: '\n' +
            'Соглашение об оказании услуг по содействию в трудоустройстве\n' +
            'Общество с ограниченной ответственностью «Вотум доверия» (ИНН XXXXX, ОГРН XXXXXX), именуемое в дальнейшем «Исполнитель», в лице Генерального директора XXXXXXX X.X., действующего на основании Устава, предоставляет любому физическому лицу, именуемому в дальнейшем «Соискатель», услуги по содействию в трудоустройстве посредством предоставления различных сервисов Сайтов Исполнителя.\n' +
            '\n' +
            'Безусловным принятием (акцептом) условий настоящей оферты считается регистрация на одном из Сайтов Исполнителя (п.1.1. Соглашения) или совершением Соискателем любых действий, направленных на какое-либо использование Сайтов Исполнителя, в том числе поиск и просмотр вакансий, осуществление откликов на вакансии, или любых других действий, направленных на использование сервисов или функционала Сайтов Исполнителя.\n' +
            '\n' +
            'Акцепт настоящей оферты (Соглашения) на иных условиях, отличных от тех, что указаны в настоящей оферте, либо акцепт под условием, не допускается.\n' +
            '\n' +
            'Термины и определения\n' +
            'Сайт — содержимое интернет-страниц, расположенных в сети интернет по адресу какого-либо из доменов, управляемых Исполнителем, включая, но не ограничивая: http://www.вотумдоверия.рф, http://www.вотумдоверия.рф, www.вотумдоверия.рф, который используется Соискателем в соответствии с настоящим Соглашением.\n' +
            'Соискатель — посетитель Сайта, прошедший регистрацию на Сайте с целью поиска работы, либо использующий Сайт также для поиска работы, но без регистрации на Сайте.\n' +
            'База данных VoteOfConfidance — совокупность резюме Соискателей, находящихся в сети интернет по адресу домена http://www.вотумдоверия.рф.\n' +
            'Учетная информация Соискателя — логин и пароль для входа на защищенные страницы Сайта. В качестве логина используется указываемый Соискателем при регистрации на Сайте адрес электронной почты или номер телефона, поддерживающий получение коротких текстовых сообщений (SMS).\n' +
            'Резюме — документ, содержащий персональные данные Соискателя, включающий информацию о фамилии и имени, адресе электронной почты, образовании, описании мест работы, а также прочих данных, относящихся к поиску работы.\n' +
            'Клиент Сайта — любое, зарегистрированное на одном из Сайтов, управляемых Исполнителем, юридическое или физическое лицо, получившие уникальные логин и пароль для входа на защищенные страницы соответствующего Сайта Исполнителя.\n' +
            'Анонимный пользователь Сайта — юридическое или физическое лицо, не зарегистрированное ни на одном из Сайтов Исполнителя или зарегистрированное только на одном из них, но использующее доступные для него сервисы Сайта (ов), на котором оно не зарегистрировано на момент использования такого Сайта.\n' +
            'Предмет соглашения\n' +
            'Исполнитель обязуется предоставить возможность Соискателю размещать Резюме на Сайте, искать вакансии, опубликованные на Сайте и создавать на них отклики.\n' +
            'В соответствии с Федеральным законом от 27.07.2006 г. № 152-ФЗ «О персональных данных» Соискатель в целях, указанных в п.2.3 настоящего Соглашения, выражает согласие Исполнителю на осуществление со всеми персональными данными, указанными Соискателем при регистрации на Сайте или в последующем на Сайте, в том числе в соответствующем Резюме, следующих действий: распространение (только в отношении Резюме или данных из Резюме), сбор, систематизация, накопление, хранение, уточнение (обновление или изменение), использование, предоставление, доступ, блокирование, уничтожение, поиск и сбор информации о Соискателе из общедоступных источников информации на основе сведений, указанных в Резюме.\n' +
            'Цель обработки персональных данных Соискателя включает в себя следующее:\n' +
            'Идентификация Соискателя как стороны по настоящему Соглашению и персонализация предоставляемых сервисов и услуг Сайта,\n' +
            'Трудоустройство у потенциальных работодателей, а также проведение предварительного собеседования в целях трудоустройства у потенциальных работодателей;\n' +
            'Направление уведомлений, запросов и информации, касающихся использования сервисов и услуг Сайта, контактирование иным способом для исполнения настоящего Соглашения, обработка запросов и обращений от Соискателя;\n' +
            'Улучшение качества сервисов и услуг Сайта, удобства их использования, разработка новых сервисов Сайта;\n' +
            'Таргетирование рекламных материалов;\n' +
            'Направление любых информационных сообщений, включая рекламу;\n' +
            'Проведение статистических и иных исследований;\n' +
            'Исполнение Исполнителем своих обязательств по настоящему Соглашению.\n' +
            'Срок размещения персональных данных в форме Резюме (срок предоставления согласия согласно п.2.2. настоящего Соглашения) неограничен и определяется самостоятельно субъектом персональных данных (Соискателем).\n' +
            'Ничто в настоящем соглашении не может пониматься как установление между Исполнителем и Соискателем агентских отношений, отношений товарищества, отношений по совместной деятельности, отношений личного найма, либо каких-то иных отношений, прямо не предусмотренных в настоящем соглашении.\n' +
            'При совершении Соискателем отклика на ту или иную вакансию от потенциального работодателя этой вакансии могут поступить вопросы, результаты ответов на которые остаются у этого работодателя и Соискателю не сообщаются, либо может поступить от потенциального работодателя предложение Соискателю пройти какой-либо тест от такого потенциального работодателя на Сайте. При этом никакие вопросы работодателей и ответы на них Соискателя не могут пониматься как единственный критерий при приеме на работу. Ответы Соискателя на вопросы потенциального работодателя являются конфиденциальными. Вся ответственность за режим конфиденциальности ответов Соискателя лежит на соответствующем потенциальном работодателе. В случае прохождения теста от потенциального работодателя по итогам его прохождения Соискателем формируется отчет, доступный для просмотра только этому потенциальному работодателю. При этом Соискателю содержание подобного отчета не предоставляется. Соискатель вправе отказаться от прохождения какого-либо теста на Сайте от потенциального работодателя. Ответы Соискателя на вопросы и содержание отчета по результатам теста от потенциального работодателя являются конфиденциальными. Вся ответственность за режим конфиденциальности ответов Соискателя и содержания отчета по итогам теста от потенциального работодателя лежит на соответствующем потенциальном работодателе.\n' +
            'За осуществление с Резюме действий, указанных в п.2.2. настоящего Соглашения, Исполнитель в пользу Соискателя никаких отчислений и\\или вознаграждений не выплачивает.\n' +
            'Исполнитель не несет ответственности за сохранение конфиденциальности и\\или за действия третьих лиц с персональными данными Соискателя, в том числе размещаемых Соискателем на Сайте в форме Резюме, в результате установления Соискателем тех или иных настроек видимости Резюме (п.3.1.3).\n' +
            'Соискатель в целях выполнения обязательств по настоящему Соглашению и достижения цели обработки персональных данных Соискателя (п.2.3. Соглашения) предоставляет согласие Исполнителю на распространение и передачу персональных данных Соискателя, предоставленных Соискателем в виде Резюме, любым третьим лицам, находящимся на территории любого иностранного государства (право Исполнителя на трансграничную передачу персональных данных Соискателя любым третьим лицам).\n' +
            'Размещение Соискателем персональных данных на Сайте в виде Резюме может производиться как непосредственно самим Соискателем путем заполнения специальной формы на Сайте, так и автоматически — путем предоставления Соискателем согласия Сайту (а также другим сайтам) программно или иным способом получить (передать) персональные данные для составления Резюме с другого (других) сайтов, на которых расположены персональные данные Соискателя с его согласия. При передаче персональных данных с другого сайта Соискатель идентифицируется по электронной почте и телефону, указанным им при регистрации на Сайте.\n' +
            'В случае составления Резюме автоматически (п.2.10.), после того как Сайт программно составит проект Резюме, Соискатель вправе либо подтвердить размещение на Сайте такого Резюме или отказаться от его размещения.\n' +
            'Заключая настоящее Соглашение, Соискатель выражает Исполнителю свое согласие на предоставление Исполнителем поручения на обработку персональных данных Соискателя, размещаемых в форме резюме на Сайте, указанных Соискателем при его регистрации на Сайте или в последующем на Сайте, любому третьему лицу на основании заключаемого с этим лицом соответствующего договора.\n' +
            'Соискатель настоящим Соглашением предоставляет свое согласие на получение от Исполнителя сообщений по сетям электросвязи, а именно: звонков, рассылок по сетям подвижной радиотелефонной связи (SMS), а также рассылок, производимых посредством систем мгновенного обмена сообщениями в режиме реального времени через Интернет (мессенджеры whatsapp/viber и другие) по контактной информации, указанной Соискателем в его Резюме на Сайте, или при его регистрации на Сайте или в последующем на Сайте. Соискатель свое согласие предоставляет на следующие указанные выше в настоящем пункте сообщения (в т.ч. рассылки и звонки) от Исполнителя: информирующие о сервисах и услугах Исполнителя, включая сервисы и услуги Сайта; отклики (приглашения) потенциальных работодателей, кадровых агентств и рекрутеров в ответ на размещенное на Сайте Соискателем Резюме или отклик Соискателя на размещенную кем-либо вакансию, если такие рассылки производятся посредством Сайта; рекламу.\n' +
            'Соискатель настоящим предоставляет свое согласие на получение информационных сообщений от Исполнителя посредством социальных сетей «Вконтакте» и «Одноклассники», в том числе сообщения: связанные с возможным последующим трудоустройством Соискателя у потенциального работодателя, о вакансиях, которые могли бы быть потенциально интересными или подходящими для Соискателя.\n' +
            'Соискатель настоящим Соглашением предоставляет свое согласие на обработку персональных данных о текущем подключении в части статистических сведений:\n' +
            'идентификатор пользователя, присваиваемый Сайтом;\n' +
            'посещенные страницы;\n' +
            'количество посещений страниц;\n' +
            'информация о перемещении по страницам сайта;\n' +
            'длительность пользовательской сессии;\n' +
            'точки входа (сторонние сайты, с которых пользователь по ссылке переходит на Сайт);\n' +
            'точки выхода (ссылки на Сайте, по которым пользователь переходит на сторонние сайты);\n' +
            'страна пользователя;\n' +
            'регион пользователя;\n' +
            'часовой пояс, установленный на устройстве пользователя;\n' +
            'провайдер пользователя;\n' +
            'браузер пользователя;\n' +
            'поисковые запросы пользователя, в том числе поисковые запросы относительно заданного пользователем местоположения;\n' +
            'цифровой отпечаток браузера (canvas fingerlirint);\n' +
            'доступные шрифты браузера;\n' +
            'установленные плагины браузера;\n' +
            'параметры WebGL браузера;\n' +
            'тип доступных медиа-устройств в браузере;\n' +
            'наличие ActiveX;\n' +
            'перечень поддерживаемых языков на устройстве пользователя;\n' +
            'архитектура процессора устройства пользователя;\n' +
            'ОС пользователя;\n' +
            'параметры экрана (разрешение, глубина цветности, параметры размещения страницы на экране);\n' +
            ' информация об использовании средств автоматизации при доступе на Сайт;\n' +
            'не персонифицированные сведения о параметрах ввода данных, параметров движения мыши, используемых комбинаций клавиш без сохранения вводимых пользователем данных.\n' +
            'Сбор указанных сведений осуществляется как механизмами самого Сайта, так и механизмами сторонних интернет-сервисов, включая, но не ограничиваясь:\n' +
            'LiveIntenet;\n' +
            'Rambler.top100;\n' +
            'ComScore;\n' +
            'TopMail.ru;\n' +
            'Google Analytics;\n' +
            'Яндекс.Метрика;\n' +
            'Group-IB Secure Portal.\n' +
            'Сбор указанных сведений производится с целью формирования статистики использования Сайта и обеспечения безопасности Сайта. В отношении зарегистрированных пользователей Сайта могут собираться сведения об использовании портов на устройствах пользователей с целью выявления подозрительной активности и защиты личных кабинетов пользователей. Исполнитель вправе изменять перечень используемых сторонних интернет-сервисов без уведомления Соискателя. Сторонние интернет-сервисы обеспечивают хранение полученных данных на собственных серверах, Исполнитель не несет ответственности за локализацию серверов сторонних интернет-сервисов. Исполнитель не производит сопоставление персональных данных о текущем подключении и сведений, предоставляемых Соискателем в форме Резюме и позволяющих идентифицировать Соискателя.\n' +
            'Соискатель выражает свое согласие с тем, что его резюме Клиент Сайта может автоматизированно забирать с Сайта посредством функционала API на Сайте (http://api.hh.ru). Исполнитель не несет ответственности перед Соискателем за действия Клиента Сайта с таким Резюме Соискателя после получения его Клиентом Сайта через функционал API Сайта.\n' +
            'Обязательства Исполнителя\n' +
            'Исполнитель обязуется:\n' +
            'предоставить возможность Соискателю создавать Резюме и хранить не более чем 20 Резюме на Сайте (сохранять в Базе данных VoteOfConfidance);\n' +
            'предоставлять доступ Клиентам Сайта и Анонимным пользователям Сайта к Резюме Соискателя;\n' +
            'предоставить возможность Соискателю управлять доступом к своему Резюме и устанавливать настройки видимости Резюме по своему выбору;\n' +
            'предоставить Соискателю возможность откликаться на опубликованные на Сайте вакансии, но не более 200 раз (не более чем 200 откликов) суммарно по всем интересующим Соискателя вакансиям на Сайте в течение 24 часов подряд (при этом не зависимо от количества резюме Соискателя, имеющихся в этот момент на Сайте); предоставить Соискателю возможность создавать сопроводительные письма и получать сообщения по электронной почте и SMS (в случае корректного указания мобильного телефона) и/или сообщения посредством функционала Сайта от Клиентов Сайта, опубликовавших вакансии;\n' +
            'предоставить возможность Соискателю удалить Резюме в любой момент по своему усмотрению.\n' +
            'Исполнитель вправе в любое время по своему усмотрению без объяснения причин удалить Резюме или отказать в его размещении.\n' +
            'Исполнитель вправе заблокировать (или удалить) аккаунт Соискателя на Сайте без возможности его восстановления в случае, если за промежуток времени в 7 календарных дней на Соискателя поступило совокупно от 2(двух) жалоб от разных Клиентов Сайта посредством использования специального функционала Сайта (кнопки «пожаловаться»).\n' +
            'Исполнитель имеет право по своему усмотрению заблокировать (или удалить) аккаунт Соискателя на Сайте без возможности его восстановления в случае наличия у Исполнителя сомнений относительно соблюдения Соискателем пп. 4.1.2 , 4.1.11, 4.1.13 настоящего Соглашения, в том числе, если у Исполнителя имеются сведения о соответствующих нарушениях и/или основания полагать, что произошло нарушение какого-либо из указанных пунктов настоящего Соглашения.\n' +
            'Исполнитель вправе в любое время и без предварительного предупреждения направлять по адресу электронной почты, указанному Соискателем при регистрации на Сайте, информационные сообщения о событиях компании Исполнителя, о сервисах Сайта и услугах Исполнителя и прочую информацию, включая рекламу.\n' +
            'Исполнитель вправе публиковать Резюме Соискателя любым образом с помощью любых средств и на любых носителях, в том числе путем размещения на Сайте и/или включения в состав Базы данных VoteOfConfidance, при этом Исполнитель не производит в адрес Соискателя никаких отчислений и\\или выплат вознаграждения.\n' +
            'Исполнитель вправе любым образом и без каких-либо ограничений извлекать прибыль из обработки и публикации персональных данных (в том числе путем предоставления к ним доступа третьих лиц) Соискателя, размещенных Соискателем на Сайте (в Базе данных VoteOfConfidance) в форме Резюме, при этом Исполнитель не производит в адрес Соискателя никаких отчислений и\\или выплат вознаграждения.\n' +
            'Исполнитель вправе в любое время предъявлять к содержанию Резюме, размещаемого Соискателем на Сайте, требования, связанные с уточнением, визуальным представлением и структурированием данных, включая расшифровку сокращений и аббревиатур, имеющихся в Резюме.\n' +
            'В случае невыполнения Соискателем требований Исполнителя, предъявленных Исполнителем в соответствии с п.3.6. настоящего Соглашения, Исполнитель вправе отказать в размещении Резюме Соискателя, в отношении которого Исполнитель предъявлял какие-либо требования.\n' +
            'По контактной информации, указанной Соискателем в Резюме на Сайте, вправе самостоятельно либо с привлечение третьего лица, которому Исполнителем предоставлено поручение на обработку персональных данных Соискателя в форме Резюме, производить звонки в целях проведения предварительного собеседования и\\или информирования по вакансиям, которые могли бы быть потенциально интересными или подходящими для Соискателя, в целях его возможного последующего трудоустройства у потенциальных работодателей, в связи с чем, заключая настоящее Соглашение, Соискатель предоставляет свое согласие на подобные действия. Соискатель также соглашается с тем, что информация, получаемая в результате подобных звонков, может предоставляться потенциальным работодателям в целях трудоустройства Соискателя у потенциальных работодателей.\n' +
            'Исполнитель вправе на основе любой информации и персональных данных, указанных в Резюме Соискателя на Сайте, предоставлять техническую возможность своим заказчикам в их интерфейсе на Сайте производить поиск на сторонних сайтах, в том числе сайтах социальных сетей, каких-либо публичных (общедоступных) профилей (аккаунтов) Соискателя и предоставлять техническую возможность заказчику отображать результаты такого поиска в его интерфейсе на Сайте. При этом Исполнитель не несет какой-либо ответственности и не возмещает какой-либо возможный ущерб за соответствие результатов поиска, а также за принятие какого-либо решения по результатам такого поиска в отношении Соискателя соответствующим заказчиком Исполнителя как потенциальным работодателем.\n' +
            'Исполнитель вправе на основе контактной информации, указанной в Резюме, направлять и/или предоставлять своим заказчикам возможность направления информационных сообщений Соискателю, связанных с его возможным последующим трудоустройством у потенциального работодателя, о вакансиях, которые могли бы быть потенциально интересными или подходящими для Соискателя посредством социальных сетей «ВКонтакте» и «Одноклассники», в том числе с привлечением третьего лица.\n' +
            'Обязательства Соискателя\n' +
            'Соискатель обязуется не совершать следующие действия:\n' +
            'Любым способом, посредством Сайта, размещать, распространять, сохранять, загружать и/или уничтожать материалы (информацию) в нарушение законодательства РФ и международного законодательства.\n' +
            'Размещать и/или передавать посредством Сайта, информацию в виде текста, изображения, звука или программного кода, которая может быть противозаконной, рекламой, угрожающей, оскорбительной, клеветнической, заведомо ложной, грубой, непристойной, вредить другим посетителям Сайта, нарушать их права и законные интересы.\n' +
            'Внедрять исполняемый код на стороне пользователя (клиентские скрипты: java-script, visual basic-script и т.п.), любые внедряемые объекты (java-апплеты, flash и т.п.), использовать frame и iframe, каскадные таблицы стилей, переопределяющие, используемые на Сайте, а также html- код, нарушающий оригинальный дизайн страницы.\n' +
            'Представляться чужим именем или от чужого имени (частного лица или организации) — зарегистрированного на Сайте соискателя, представителя организации работодателя, сотрудника Исполнителя. Вводить в заблуждение пользователей и администрацию Сайта относительно своей идентификации любым иным способом.\n' +
            'Размещать заведомо недостоверную информацию.\n' +
            'Размещать объявления, рекламирующие любые франчайзинговые или «пирамидальные» схемы, предлагающие «вступить в клуб», стать дистрибьютором, торговым представителем, «менеджером» или иным сотрудником компании, бизнес модель которой основана на предварительной и/или периодической передаче денежных средств от нижестоящих сотрудников вышестоящим, подразумевающую оплату труда только в виде процента от продаж и/или требующую привлечения или найма других агентов, дистрибьюторов, «менеджеров», «членов клуба» и тому подобное.\n' +
            'Размещать и/или передавать, используя Сайт, материалы, в случае, если Соискатель не имеет на это соответствующих прав. Это касается материалов, защищенных авторскими правами, торговыми марками, патентами, а также соглашениями о нераспространении информации, конфиденциальности и тому подобными.\n' +
            'Уничтожать и/или изменять любые материалы на Сайте, автором которых Соискатель не является.\n' +
            'Распечатывать или иным способом копировать и использовать персональную информацию, кроме своего Резюме.\n' +
            'Использовать информацию о телефонах, почтовых адресах, адресах электронной почты для целей иных, нежели тематика Сайта (вопросы найма на работу, подбора кандидатов, предложения позиций и кандидатур и тому подобное).\n' +
            'Регистрироваться, используя чужой e-mail адрес, или адрес, на который у Соискателя нет права использования его подобным образом.\n' +
            'Размещать вакансии любых работодателей, а именно предложений о возможности трудоустройства у любых работодателей.\n' +
            'Размещать номера телефона(ов), право на использование которых отсутствует у соискателя, либо номер принадлежит юридическим лицам.\n' +
            'Соискатель, акцептуя настоящую оферту, выражает свое полное и безоговорочное согласие получать в соответствии с п.3.3. Соглашения от Исполнителя по электронной почте любые информационные сообщения, включая рекламу.\n' +
            'Соискатель обязуется регулярно знакомиться с содержанием настоящего Соглашения, в целях своевременного ознакомления с его изменениями.\n' +
            'Соискатель вправе в любое время по своему усмотрению отозвать свое согласие на обработку своих персональных данных (представленных в виде Резюме) Исполнителем в том числе путем самостоятельного удаления какого-либо конкретного либо всех своих Резюме, размещенных на Сайте. Для чего Соискателю предоставляется на Сайте соответствующая техническая возможность (интерфейс).\n' +
            'Соискатель вправе в любое время по своему усмотрению удалить свой аккаунт (регистрацию) на Сайте, включая все данные, относящиеся к данному аккаунту (регистрации), направив посредством использования своей Учетной информации на Сайте соответствующее обращение (просьбу об удалении аккаунта) в Сообщество поддержки VoteOfConfidance, расположенному по адресу: http://feedback.hh.ru, либо направить соответствующее обращение (просьбу об удалении аккаунта) по адресу электронной почты feedback@hh.ru, используя при этом в качестве адреса отправления такого обращения тот же адрес электронной почты, что и при регистрации своего аккаунта (регистрации) на Сайте.\n' +
            'Информационная безопасность Сайта\n' +
            'Используя Сайт, Соискатель обязуется не нарушать или пытаться нарушать информационную безопасность Сайта, что включает в себя:\n' +
            'доступ к данным, не предназначенным для данного Соискателя или вход в систему под логином, не принадлежащем данному Соискателю;\n' +
            'попытки проверить уязвимость системы безопасности Сайта, нарушение процедуры регистрации и авторизации без разрешения Исполнителя;\n' +
            'попытки создать помехи в использовании Сайта другим пользователям, что включает в себя распространение компьютерных вирусов, порчу данных, постоянную рассылку повторяющейся информации, пересылку электронной почты через сервер сайта, одновременную отправку большого количества электронной почты и/или запросов к Сайту с целью намеренно вывести сервер сайта из строя и тому подобные действия, выходящие за рамки нормального целевого использования Сайта, и могущие умышленно или по неосторожности повлечь сбои в его работе;\n' +
            'рассылку пользователям Сайта материалов, на которые они не давали своего согласия, «спама», любых писем и рекламы без разрешения Исполнителя;\n' +
            'имитацию и/или подделку любого заголовка пакета TCP/IP или любой части заголовка в любом электронном письме или размещенном на Сайте материале;\n' +
            'использование или попытки использования любого программного обеспечения, или процедуры для навигации или поиску на Сайте, кроме встроенной в Сайт поисковой машины и традиционных и общедоступных браузеров (Microsoft Explorer, Mozilla Firefox и других подобных).\n' +
            'Нарушение Соискателем безопасности системы или компьютерной сети или попыток ее нарушить влечет за собой гражданскую и уголовную ответственность. Исполнитель будет расследовать все случаи возможного нарушения безопасности или попыток ее нарушить со стороны всех пользователей Сайта в сотрудничестве с правоохранительными органами с целью пресечения подобной злонамеренной деятельности.\n' +
            'Защищенные разделы Сайта и пароли\n' +
            'Доступ к информации, находящейся на защищенных разделах Сайта разрешен только зарегистрированным пользователям, получившим пароль для входа на защищенные разделы Сайта. Пароль не может передаваться другим лицам, и Соискатель полностью несет ответственность за весь ущерб, причиненный ему, Исполнителю или третьим лицам, возникший вследствие намеренной или ненамеренной передачи Соискателем пароля другому лицу. Соискатель несет ответственность за сохранение конфиденциальности пароля и любое использование Сайта посредством его пароля.\n' +
            'Информация о пользователе\n' +
            'Исполнитель прилагает все возможные усилия для того, чтобы избежать несанкционированного использования персональных данных Соискателя.\n' +
            'Исполнитель не несет ответственности за возможное нецелевое использование персональных данных Соискателей, произошедшее из-за:\n' +
            'технических неполадок в программном обеспечении, серверах или компьютерных сетях, находящихся вне контроля Исполнителя;\n' +
            'перебоев в работе Сайта, связанных с намеренным или ненамеренным использованием Сайта не по назначению третьими лицами, описанным в разделе «Информационная безопасность Сайта»;\n' +
            'передачи паролей или любой информации, включая как Резюме в целом, так и его отдельных частей или данных, с Сайта пользователями другим лицам, не являющимися зарегистрированными пользователями Сайта или другим пользователям, не имеющим доступа к данной информации в силу условий регистрации и заключенных договоров с Исполнителем;\n' +
            'нарушения информационной безопасности Сайта;\n' +
            'нарушениями условий договоров на использование Базы данных VoteOfConfidance со стороны соответствующих контрагентов Исполнителя (Клиентов Сайта);\n' +
            'Используя Сайт, Соискатель соглашается и принимает, что Исполнитель оставляет за собой право использовать его персональные данные, в том числе представленные в виде Резюме, анонимно и в обобщенном виде для статистических целей, а также для таргетинга рекламы, размещаемой на Сайте.\n' +
            'Исполнитель обязуется не предоставлять никакие персональные данные Соискателя физическим и юридическим лицами, заявляющим о возможном нецелевом использовании подобной информации (рассылки несанкционированной рекламы, «спама», предоставлении информации другим лицам и тому подобное).\n' +
            'Используя Сайт, Соискатель соглашается и осознает, что при отправке им отклика на ту или иную вакансию, размещенную на Сайте кадровым агентством, организацией или иным лицом, оказывающим услуги по поиску и подбору персонала, указанные лица вправе передавать персональные данные данного Соискателя в виде Резюме своим клиентам (заказчикам) в целях рассмотрения Соискателя в качестве потенциального кандидата на вакантные позиции у этих клиентов (заказчиков). Соискатель также соглашается и осознает, что при использовании настроек видимости Резюме, позволяющих доступ к нему со стороны кадровых агентств, организаций или иных лиц, оказывающих услуги по поиску и подбору персонала, соответствующие кадровые агентства, организации или иные лица вправе передавать персональные данные данного Соискателя в виде Резюме своим клиентам (заказчикам) в целях рассмотрения Соискателя в качестве потенциального кандидата на вакантные позиции у этих клиентов (заказчиков). При этом у такого кадрового агентства, организации или иного лица, разместившего какую-либо вакансию на Сайте, отсутствует какая-либо возможность установить, что в отношении их клиентов (заказчиков) на Сайте Соискателем установлено какое-либо ограничение видимости (доступности) Резюме Соискателя.\n' +
            'Финансовые отношения\n' +
            'Услуги Исполнителя, оказываемые согласно п.2.1. настоящего Соглашения для Соискателей оказываются БЕЗ ОПЛАТЫ.\n' +
            'Исполнитель не является представителем ни Соискателей, публикующих на Сайте свои резюме, ни Клиентов Сайта, размещающих вакансии, поэтому не может отвечать ни за какие обязательства (включая финансовые) возникающие между теми и другими. Любые договоренности между соискателями и Клиентами Сайта, являются двусторонними, и Исполнитель не имеет к ним отношения.\n' +
            'Использование материалов Сайта\n' +
            'Каждый пользователь Сайта, Соискатель или представитель Клиента Сайта, отвечает за информацию, размещаемую от его имени и за последствия этого размещения.\n' +
            'Сайт является лишь средством для передачи информации и ни в коем случае не несет ответственности за ее достоверность и актуальность.\n' +
            'Исполнитель прилагает все возможные усилия для того, чтобы исключить с Сайта небрежную, неаккуратную или заведомо неполную информацию, однако, в конечном счете, ответственность за нее лежит на разместивших ее лицах.\n' +
            'При перепечатке и ином использовании материалов Сайта, не являющихся Резюме Соискателей, описаниями компаний или вакансий, а также логотипом, элементами дизайна, внешнего вида и структуры Сайта, ссылка на Сайт обязательна.\n' +
            'Использование Резюме Соискателей, описаний компаний и вакансий недопустимо ни с какими целями, кроме соответствующих тематике Сайта (поиск работы, сотрудников, получение информации о рынке труда).\n' +
            'В случае, если Клиент Сайта сохраняет Резюме Соискателя в своей собственной базе данных вне Сайта, то ответственность за это несет Клиент Сайта в рамках ФЗ № 152-ФЗ от 27 июля 2006 г. «О персональных данных», как оператор персональных данных.\n' +
            'Не допускается использование программных средств (скриптов, роботов) для считывания или сбора информации (данных) с Сайта.\n' +
            'Логотип, название, элементы дизайна, оформления и общий внешний вид Сайта являются интеллектуальной собственностью Исполнителя и их использование без прямого явного согласия Исполнителя запрещено.\n' +
            'Поскольку идентификация пользователей Сайтов затруднена по техническим причинам, Исполнитель не отвечает за то, что зарегистрированные пользователи являются действительно теми людьми, за кого себя выдают, и не несет ответственности за возможный ущерб, причиненный Соискателям или другим лицам по этой причине.\n' +
            'Используя информацию с Сайта, Соискатель осознает и принимает риски, связанные с возможной недостоверностью размещенной на Сайте информации, а также с тем, что некоторая информация может показаться ему угрожающей, оскорбительной, клеветнической, заведомо ложной, грубой, непристойной. Если это произошло, Соискатель должен немедленно прекратить использовать Сайт и сообщить Исполнителю о наличии подобной информации.\n' +
            'Исполнитель не гарантирует, что опубликованные Резюме будут просмотрены определенным количеством Клиентов Сайта или хотя бы одним.\n' +
            'Исполнитель не гарантирует, что программное обеспечение, сервера и компьютерные сети, используемые Сайтом свободны от ошибок и компьютерных вирусов. Если использование Сайта повлекло за собой утрату данных или порчу оборудования Исполнитель не несет за это ответственности.\n' +
            'Исполнитель не гарантирует, что условия работы (например наименование должности, должностные обязанности, оклад и условия оплаты труда и другие), содержащиеся в размещенных на Сайте вакансиях, будут соответствовать тем, на которые был принят на работу Соискатель.\n' +
            'Исполнитель не несет ответственности за содержание размещенных на Сайте вакансий. За содержание размещенных на Сайте вакансий несут ответственность Клиенты Сайта, публикующие соответствующие вакансии.\n' +
            'Файлы Cookie\n' +
            'Программное обеспечение Сайта может передавать программному обеспечению Соискателя файлы Cookie или данные, включаемые в состав файлов Cookie, так же как и программное обеспечение Соискателя может передавать Сайту файлы Cookie или данные, включаемые в состав файлов Cookie.\n' +
            'Исполнитель вправе использовать любым способом файлы Cookie для целей идентификации Соискателя, а также для любых иных целей, в том числе для предоставления персонализированных сервисов и услуг Сайта, таргетирования рекламы, проведения исследований и других целей.\n' +
            'Содержание, структура и любые иные характеристики файлов Cookie определяется Исполнителем по своему усмотрению.\n' +
            'Право, подлежащие применению к отношениям Сторон, и разрешение споров\n' +
            'Настоящее Соглашение, а также отношения между Соискателем и Исполнителем, в том числе, не урегулированные настоящим Соглашением, регулируются в соответствии с действующим законодательством Российской Федерации.\n' +
            'В случае невозможности разрешения споров между Сторонами путем переговоров, они должны разрешаться в суде по месту государственной регистрации Исполнителя в порядке, установленном действующим процессуальным законодательством Российской Федерации.\n' +
            'Действие настоящего Соглашения, внесение изменений\n' +
            'Настоящее Соглашение действует с момента его акцепта Соискателем.\n' +
            'Соискатель, заключая настоящее Соглашение, заявляет и гарантирует, что достиг возраста, при котором Соискателю действующим законодательством РФ допустимо заключать настоящее Соглашение, и Соискатель обладает необходимой право- и дееспособностью для заключения настоящего Соглашения.\n' +
            'Исполнитель вправе в любое время по своему усмотрению и без предварительного согласования с Соискателем вносить изменения в настоящее Соглашение. В таком случае изменения и дополнения вступают в силу с момента их опубликования на Сайте.\n' +
            'Использование Соискателем Сайта (а равно реализация Соискателем прав и обязанностей, установленных настоящим Соглашением) после любых изменений настоящего Соглашения означает согласие Соискателя с такими изменениями и/или дополнениями.\n' +
            'Если Соискатель не согласен использовать Сайт после внесения изменений в настоящее Соглашение Исполнителем и\\или соответственно соблюдать настоящее Соглашение после изменений, то Соискатель обязуется прекратить использование Сайта и вправе расторгнуть настоящее Соглашение, направив соответствующее уведомление Исполнителю.\n' +
            'Исполнитель вправе в любое время, в том числе в случае нарушения Соискателем условий настоящего Соглашения, расторгнуть настоящее Соглашение в одностороннем порядке без какого-либо предварительного уведомления Соискателя об этом и какой-либо компенсации в связи с этим в адрес Соискателя. В случае расторжения настоящего Соглашения Исполнитель удаляет Учетные данные Соискателя, Резюме, размещенные Соискателем на Сайте, и прочие материалы и информацию.\n' +
            '© 2019 Группа компаний Вотум Доверия',
        loremIpsum: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
    }
});