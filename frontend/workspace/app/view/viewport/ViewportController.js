Ext.define('VocApp.view.viewport.ViewportController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.viewport',

    listen: {
        controller: {
            '*': {
                unmatchedroute: 'handleUnmatchedRoute'
            }
        }
    },

    routes: {
        'home': 'handleHomeRoute',
        'faq': 'handleFaqRoute',
        'agreement': 'handleAgreementRoute'
    },

    onLaunch: function () {
    },

    showView: function (xtype) {
        let view = this.lookup(xtype),
            viewport = this.getView();
        if (!view) {
            viewport.removeAll(true);
            view = viewport.add({
                xtype: xtype,
                reference: xtype
            });
        }

        viewport.setActiveItem(view);
    },
    
    // ROUTING

    handleHomeRoute: function () {
        this.showView('home');
    },

    handleFaqRoute: function () {
        this.showView('faq');
    },
    handleAgreementRoute: function () {
        this.showView('agreement');
    },

    handleUnmatchedRoute: function (route) {
        let me = this;

        if (!me.session || !me.session.isValid()) {
            // There is no authenticated user, let's redirect to the login page but keep track
            // of the original route to restore the requested route after user authentication.
            me.originalRoute = route;
            me.redirectTo('home', {replace: true});
            return;
        }

        // There is an authenticated user, so let's simply redirect to the default token.
        let target = VocApp.getApplication().getDefaultToken();
        Ext.log.warn('Route unknown: ', route);
        if (route !== target) {
            me.redirectTo(target, {replace: true});
        }
    }

    
});

