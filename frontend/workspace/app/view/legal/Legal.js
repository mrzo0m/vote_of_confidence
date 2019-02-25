/**
 * Created by olegbursinov on 2019-02-24.
 */
Ext.define('VocApp.view.legal.Legal', {
    extend: 'Ext.Container',

    requires: [
        'VocApp.view.legal.LegalModel',
		'VocApp.view.legal.LegalController'
    ],

    xtype: 'legal',
    itemId: 'legal',
    viewModel: {
        type: 'legal'
    },
    layout: 'fit',

    controller: 'legal',

    items: [
        {
            xtype: 'panel',
            bodyPadding: 20,
            header: {
                userCls: 'lockscreen-header',
                padding: '10 20',
                title: {
                    text: '<b>Дмитрий Иванов</b><br>Project Manager',
                    icon: 'resources/images/user-profile/2.png'
                }
            },
            defaults: {
                margin: '0 0 10 0'
            },
            items: [
                {
                xtype: 'button',
                text: 'Зарегистрироваться',
                iconAlign: 'right',
                iconCls: 'x-fa fa-angle-right',
                width: '100%',
                ui: 'gray-button',
                handler: 'goToDashboard'
            }, {
                xtype: 'component',
                margin: 0,
                html: 'Нажимая «Зарегистрироваться»,' +
                    ' вы подтверждаете, что ознакомлены, полностью согласны' +
                    ' и принимаете условия <a href="#agreement">«Соглашения об оказании услуг по содействию в трудоустройстве (оферта)»</a>'
            }]
        }

    ]
});