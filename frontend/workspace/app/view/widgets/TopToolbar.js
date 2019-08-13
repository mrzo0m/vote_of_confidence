/**
 * Created by 16678596 on 15.07.2019.
 */
Ext.define('VocApp.view.widgets.TopToolbar', {
    extend: 'Ext.Toolbar',
    xtype: 'toptoolbar',

    config: {
        expanded: false
    },

    classCls: 'toptoolbar',

    initialize: function() {
        var me = this;
        me.callParent();
    },
    docked: 'top'

});