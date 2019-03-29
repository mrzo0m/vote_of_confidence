/**
 * Created by 16678596 on 28.03.2019.
 */
Ext.define('VocApp.view.main.PageTemplateView', {
    extend: 'Ext.navigation.View',

    requires: [
        'Ext.Button',
        'Ext.list.Tree',
        'Ext.MessageBox',
        'VocApp.view.widgets.Widgets',
        'Ext.layout.Fit',
        'Ext.navigation.View'
    ],

    navigationBar: false,

    xtype: 'pagetemplate',

    items: [
        {
            xtype: 'maintoolbar',
            docked: 'top',
            shadow: true
        }
    ]
});