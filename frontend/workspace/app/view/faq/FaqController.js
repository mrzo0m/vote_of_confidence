/**
 * Created by 16678596 on 28.03.2019.
 */
Ext.define('VocApp.view.faq.FaqController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.faq',
    initComponent: function() {
        // this function runs between ctor #1 and ctor #2

        // initComponent #1 - the UI component object tree is created,
        // (this object and child objects from config { items: [{...}]})
        // but they have not yet been rendered to DOM or shown.
        this.callParent(arguments);

        // initComponent #2 - I believe this is equivalent to ctor #2,
        // I would prefer ctor as it is more universal.
    }
});