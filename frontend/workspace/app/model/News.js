Ext.define('VocApp.model.News', {
    extend: 'VocApp.model.Base',

    fields: [
        'type',
        { name: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s' },
        'time',
        'author',
        'group',
        'image',
        'title',
        'paragraph'
    ]
});
