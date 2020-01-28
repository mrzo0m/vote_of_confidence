<template>
    <v-app id="inspire">
        <v-navigation-drawer
                :clipped="$vuetify.breakpoint.lgAndUp"
                app
                v-model="drawer"
        >
            <v-list dense>
                <template v-for="item in items">
                    <v-row
                            :key="item.heading"
                            align="center"
                            v-if="item.heading"
                    >
                        <v-col cols="6">
                            <v-subheader v-if="item.heading">
                                {{ item.heading }}
                            </v-subheader>
                        </v-col>
                        <v-col
                                class="text-center"
                                cols="6"
                        >
                            <a
                                    class="body-2 black--text"
                                    href="#!"
                            >EDIT</a>
                        </v-col>
                    </v-row>
                    <v-list-group
                            :key="item.text"
                            :prepend-icon="item.model ? item.icon : item['icon-alt']"
                            append-icon=""
                            v-else-if="item.children"
                            v-model="item.model"
                    >
                        <template v-slot:activator>
                            <v-list-item-content>
                                <v-list-item-title>
                                    {{ item.text }}
                                </v-list-item-title>
                            </v-list-item-content>
                        </template>
                        <v-list-item
                                :key="i"
                                link
                                v-for="(child, i) in item.children"
                        >
                            <v-list-item-action v-if="child.icon">
                                <v-icon>{{ child.icon }}</v-icon>
                            </v-list-item-action>
                            <v-list-item-content>
                                <v-list-item-title>
                                    {{ child.text }}
                                </v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-group>
                    <v-list-item
                            :key="item.text"
                            link
                            v-else
                    >
                        <v-list-item-action>
                            <v-icon>{{ item.icon }}</v-icon>
                        </v-list-item-action>
                        <v-list-item-content>
                            <v-list-item-title>
                                {{ item.text }}
                            </v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </template>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar
                :clipped-left="$vuetify.breakpoint.lgAndUp"
                app
                color="white darken-3"
                light
        >
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>
            <v-toolbar-title
                    class="ml-0 pl-4"
                    style="width: 300px"
            >
                <span class="hidden-sm-and-down">Вотум доверия</span>
            </v-toolbar-title>
            <v-spacer/>
            <v-btn icon>
                <v-icon>mdi-apps</v-icon>
            </v-btn>
            <v-btn icon>
                <v-icon>mdi-bell</v-icon>
            </v-btn>
            <v-btn
                    icon
                    large
            >
                <v-avatar
                        item
                        size="32px"
                >
                    <v-img
                            alt="Vuetify"
                            src="https://cdn.vuetifyjs.com/images/logos/logo.svg"
                    />
                </v-avatar>
            </v-btn>
        </v-app-bar>
        <v-content>
            <v-container
                    class="fill-height"
                    fluid
            >
                <v-row
                        align="center"
                        justify="center"
                >
                    <v-tooltip right>
                        <template v-slot:activator="{ on }">
                            <v-btn
                                    :href="source"
                                    icon
                                    large
                                    target="_blank"
                                    v-on="on"
                            >
                                <v-icon large>mdi-code-tags</v-icon>
                            </v-btn>
                        </template>
                        <span>Source</span>
                    </v-tooltip>
                    <v-tooltip right>
                        <template v-slot:activator="{ on }">
                            <v-btn
                                    href="https://codepen.io/johnjleider/pen/MNYLdL"
                                    icon
                                    large
                                    target="_blank"
                                    v-on="on"
                            >
                                <v-icon large>mdi-codepen</v-icon>
                            </v-btn>
                        </template>
                        <span>Codepen</span>
                    </v-tooltip>
                </v-row>
            </v-container>
        </v-content>
        <v-btn
                @click="dialog = !dialog"
                bottom
                color="pink"
                fab
                fixed
                light
                right
        >
            <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-dialog
                v-model="dialog"
                width="800px"
        >
            <v-card>
                <v-card-title class="grey darken-2">
                    Create contact
                </v-card-title>
                <v-container>
                    <v-row class="mx-2">
                        <v-col
                                class="align-center justify-space-between"
                                cols="12"
                        >
                            <v-row
                                    align="center"
                                    class="mr-0"
                            >
                                <v-avatar
                                        class="mx-3"
                                        size="40px"
                                >
                                    <img
                                            alt=""
                                            src="//ssl.gstatic.com/s2/oz/images/sge/grey_silhouette.png"
                                    >
                                </v-avatar>
                                <v-text-field
                                        placeholder="Name"
                                />
                            </v-row>
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                    placeholder="Company"
                                    prepend-icon="mdi-account-card-details-outline"
                            />
                        </v-col>
                        <v-col cols="6">
                            <v-text-field
                                    placeholder="Job title"
                            />
                        </v-col>
                        <v-col cols="12">
                            <v-text-field
                                    placeholder="Email"
                                    prepend-icon="mdi-mail"
                            />
                        </v-col>
                        <v-col cols="12">
                            <v-text-field
                                    placeholder="(000) 000 - 0000"
                                    prepend-icon="mdi-phone"
                                    type="tel"
                            />
                        </v-col>
                        <v-col cols="12">
                            <v-text-field
                                    placeholder="Notes"
                                    prepend-icon="mdi-text"
                            />
                        </v-col>
                    </v-row>
                </v-container>
                <v-card-actions>
                    <v-btn
                            color="primary"
                            text
                    >More
                    </v-btn>
                    <v-spacer/>
                    <v-btn
                            @click="dialog = false"
                            color="primary"
                            text
                    >Cancel
                    </v-btn>
                    <v-btn
                            @click="dialog = false"
                            text
                    >Save
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-content>
            <HelloWorld/>
        </v-content>
    </v-app>
</template>
<script lang="ts">
        import Vue from 'vue';
        import HelloWorld from './components/HelloWorld.vue';

        export default Vue.extend({
            name: 'App',

            components: {
                HelloWorld,
            },
            props: {
                source: String,
            },
            data: () => ({
                dialog: false,
                drawer: null,
                items: [
                    {icon: 'mdi-help-circle', text: 'О сообществе'},
                    {
                        icon: 'mdi-chevron-up',
                        'icon-alt': 'mdi-chevron-down',
                        text: 'Горизонт',
                        model: true,
                        children: [
                            {icon: 'mdi-contacts', text: 'Эксперт'},
                            {icon: 'mdi-history', text: 'Кондидат'},
                            {icon: 'mdi-content-copy', text: 'Компания заказчик'},
                        ],
                    }
                ],
            }),
        });
</script>