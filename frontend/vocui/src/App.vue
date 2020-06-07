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
            <v-btn icon id='logout-button' v-if='authenticated' v-on:click='logout'>
                Выход
            </v-btn>
            <v-btn icon id='login-button' v-else v-on:click='login'>
                Вход
            </v-btn>
            <v-btn icon>
                <v-icon>mdi-bell</v-icon>
            </v-btn>
            <v-img max-height="32px" max-width="32px" alt="voc logo"  src="./assets/voc_logo.png"></v-img>
        </v-app-bar>
        <v-content>
            <v-container>
                <v-card
                        class="mx-auto"
                >
                    <v-list-item>
                        <v-list-item-avatar color="grey"></v-list-item-avatar>
                        <v-list-item-content>
                            <v-list-item-title class="headline">Our Changing Planet</v-list-item-title>
                            <v-list-item-subtitle>by Kurt Wagner</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>

                    <v-img
                            height="380"
                            src="https://cdn.vuetifyjs.com/images/cards/mountain.jpg"
                    ></v-img>

                    <v-card-text v-text="lorum">

                    </v-card-text>

                    <v-card-actions>
                        <v-btn
                                color="deep-purple accent-4"
                                text
                        >
                            Read
                        </v-btn>
                        <v-btn
                                color="deep-purple accent-4"
                                text
                        >
                            Bookmark
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn icon>
                            <v-icon>mdi-heart</v-icon>
                        </v-btn>
                        <v-btn icon>
                            <v-icon>mdi-share-variant</v-icon>
                        </v-btn>
                    </v-card-actions>
                </v-card>
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
<script>
    import Vue from 'vue';

    export default Vue.extend({
        name: 'App',
        components: {},
        props: {
            source: String,
        },

        data: () => ({
            authenticated: false,
            dialog: false,
            drawer: null,
            textblocks: [
                {
                    color: '#dbfff8',
                    src: 'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                    title: 'Supermodel',
                    artist: 'Foster the People',
                },
                {
                    color: '#c7fffd',
                    src: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png',
                    title: 'Halcyon Days',
                    artist: 'Ellie Goulding',
                },
            ],
            lorum: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Non consectetur a erat nam at lectus urna duis. Sed pulvinar proin gravida hendrerit lectus. Id ornare arcu odio ut sem. Amet massa vitae tortor condimentum lacinia. Nunc eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Egestas sed sed risus pretium quam vulputate dignissim suspendisse in. Nam at lectus urna duis convallis. Auctor augue mauris augue neque gravida in. Amet volutpat consequat mauris nunc congue nisi. Turpis massa tincidunt dui ut ornare lectus.\n' +
                '\n' +
                '                                Amet facilisis magna etiam tempor orci eu. Integer eget aliquet nibh praesent tristique magna sit. Sed sed risus pretium quam. Tincidunt id aliquet risus feugiat in ante metus dictum. Ac turpis egestas maecenas pharetra convallis. Metus vulputate eu scelerisque felis imperdiet proin fermentum. Aliquet enim tortor at auctor urna nunc id cursus metus. Imperdiet sed euismod nisi porta lorem mollis aliquam ut porttitor. Feugiat in fermentum posuere urna nec tincidunt. Sagittis nisl rhoncus mattis rhoncus urna neque viverra justo nec. Nisl nunc mi ipsum faucibus vitae aliquet. Pellentesque habitant morbi tristique senectus. Nunc lobortis mattis aliquam faucibus. A pellentesque sit amet porttitor eget.\n' +
                '\n' +
                '                                Sit amet consectetur adipiscing elit ut aliquam purus. Sed risus pretium quam vulputate dignissim. Ridiculus mus mauris vitae ultricies leo integer malesuada nunc vel. Tellus in metus vulputate eu scelerisque. Neque laoreet suspendisse interdum consectetur libero id. Pretium vulputate sapien nec sagittis aliquam malesuada. Adipiscing bibendum est ultricies integer quis auctor elit sed. Ornare massa eget egestas purus. Viverra adipiscing at in tellus integer feugiat scelerisque. Faucibus turpis in eu mi bibendum. Vestibulum morbi blandit cursus risus at. Pretium lectus quam id leo in vitae. Quisque egestas diam in arcu cursus euismod quis viverra. Praesent elementum facilisis leo vel fringilla est ullamcorper eget. Ac tortor vitae purus faucibus ornare suspendisse sed. Tincidunt id aliquet risus feugiat in. A lacus vestibulum sed arcu non odio euismod.\n' +
                '\n' +
                '                                Ac tortor dignissim convallis aenean. Pellentesque elit eget gravida cum sociis natoque penatibus et magnis. Fermentum odio eu feugiat pretium nibh ipsum consequat nisl. Pellentesque diam volutpat commodo sed egestas egestas fringilla phasellus faucibus. Id porta nibh venenatis cras. Ac turpis egestas sed tempus urna et pharetra pharetra massa. Curabitur vitae nunc sed velit dignissim sodales ut. Posuere lorem ipsum dolor sit amet. Sapien faucibus et molestie ac feugiat sed lectus vestibulum mattis. Adipiscing elit duis tristique sollicitudin nibh sit amet commodo. Nascetur ridiculus mus mauris vitae ultricies leo integer. Adipiscing elit pellentesque habitant morbi tristique senectus et netus et. Dictumst vestibulum rhoncus est pellentesque elit ullamcorper dignissim. Odio morbi quis commodo odio. Nisl vel pretium lectus quam id leo.\n' +
                '\n' +
                '                                Vitae semper quis lectus nulla at. Convallis a cras semper auctor neque. Egestas pretium aenean pharetra magna ac placerat vestibulum. Gravida arcu ac tortor dignissim convallis aenean et. Mauris cursus mattis molestie a iaculis at erat. Risus quis varius quam quisque id diam vel quam elementum. Cras semper auctor neque vitae tempus quam pellentesque nec. A lacus vestibulum sed arcu non odio. Interdum velit laoreet id donec ultrices. Aliquam id diam maecenas ultricies mi eget. Varius duis at consectetur lorem donec. Libero enim sed faucibus turpis in eu mi bibendum. Mi tempus imperdiet nulla malesuada pellentesque elit eget. Hac habitasse platea dictumst quisque sagittis purus sit. Eget nulla facilisi etiam dignissim diam. Ac felis donec et odio pellentesque diam volutpat. Non sodales neque sodales ut. Urna duis convallis convallis tellus id interdum. Sollicitudin nibh sit amet commodo nulla facilisi nullam.\n' +
                '\n' +
                '                                Tortor vitae purus faucibus ornare suspendisse. Sit amet justo donec enim. Id porta nibh venenatis cras sed. Enim ut sem viverra aliquet eget sit amet tellus cras. Faucibus a pellentesque sit amet porttitor eget. Donec adipiscing tristique risus nec. Magna eget est lorem ipsum. Facilisis volutpat est velit egestas dui id. Amet venenatis urna cursus eget. Euismod elementum nisi quis eleifend quam adipiscing. Sed velit dignissim sodales ut. Tellus molestie nunc non blandit massa. Proin fermentum leo vel orci. In vitae turpis massa sed elementum tempus. Amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet. Tortor at risus viverra adipiscing at in. Quis imperdiet massa tincidunt nunc pulvinar sapien et. Nibh cras pulvinar mattis nunc. Aliquet lectus proin nibh nisl condimentum id. In ante metus dictum at tempor commodo ullamcorper.\n' +
                '\n' +
                '                                Lectus proin nibh nisl condimentum id. Pellentesque massa placerat duis ultricies lacus sed turpis. Ultricies integer quis auctor elit sed vulputate. Lobortis feugiat vivamus at augue eget. Morbi blandit cursus risus at ultrices. Tempor id eu nisl nunc. Vulputate mi sit amet mauris commodo. Mi sit amet mauris commodo quis imperdiet massa tincidunt nunc. Proin fermentum leo vel orci porta non pulvinar neque laoreet. Massa ultricies mi quis hendrerit dolor magna eget est lorem. Nisl purus in mollis nunc sed. Ullamcorper morbi tincidunt ornare massa eget. Nec ultrices dui sapien eget mi proin sed libero. Varius sit amet mattis vulputate. Mus mauris vitae ultricies leo integer malesuada. Nisl purus in mollis nunc sed. Dictum varius duis at consectetur lorem donec massa sapien faucibus. Magnis dis parturient montes nascetur ridiculus mus mauris vitae. Id interdum velit laoreet id donec.\n' +
                '\n' +
                '                                Urna et pharetra pharetra massa massa ultricies. Volutpat est velit egestas dui id ornare arcu odio ut. Pharetra pharetra massa massa ultricies mi. Platea dictumst quisque sagittis purus sit amet volutpat. Odio ut enim blandit volutpat maecenas. Sed nisi lacus sed viverra tellus in. Sit amet est placerat in. In nulla posuere sollicitudin aliquam ultrices sagittis orci a. Cursus euismod quis viverra nibh cras pulvinar mattis. In hac habitasse platea dictumst. Eu non diam phasellus vestibulum lorem sed risus ultricies. Proin sed libero enim sed faucibus turpis in eu. Eget duis at tellus at urna condimentum mattis. Pretium vulputate sapien nec sagittis. Nibh venenatis cras sed felis eget velit. Urna nec tincidunt praesent semper feugiat nibh. Tortor at auctor urna nunc id cursus metus aliquam. Tincidunt dui ut ornare lectus sit amet est placerat. Pharetra diam sit amet nisl suscipit adipiscing bibendum est ultricies.\n' +
                '\n' +
                '                                Purus gravida quis blandit turpis. Ut lectus arcu bibendum at. Orci nulla pellentesque dignissim enim sit amet. Ornare suspendisse sed nisi lacus. Rhoncus urna neque viverra justo nec ultrices. Mauris pellentesque pulvinar pellentesque habitant. Auctor eu augue ut lectus arcu bibendum at varius vel. Volutpat consequat mauris nunc congue nisi vitae suscipit. Mollis aliquam ut porttitor leo a. Amet tellus cras adipiscing enim. Vel facilisis volutpat est velit egestas dui id ornare. At varius vel pharetra vel.\n' +
                '\n' +
                '                                Consectetur libero id faucibus nisl tincidunt eget nullam non nisi. Volutpat odio facilisis mauris sit amet. Cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris. Pellentesque pulvinar pellentesque habitant morbi tristique. Adipiscing elit pellentesque habitant morbi tristique. Ac turpis egestas integer eget. Ornare arcu dui vivamus arcu felis bibendum. Facilisis magna etiam tempor orci eu lobortis elementum. At tellus at urna condimentum mattis pellentesque. Senectus et netus et malesuada fames ac turpis.',
            items: [
                {icon: 'mdi-help-circle', text: 'О сообществе'},
                {
                    icon: 'mdi-chevron-up',
                    'icon-alt': 'mdi-chevron-down',
                    text: 'Горизонт',
                    model: true,
                    children: [
                        {icon: 'mdi-contacts', text: 'Эксперт'},
                        {icon: 'mdi-history', text: 'Кандидат'},
                        {icon: 'mdi-content-copy', text: 'Компания заказчик'},
                    ],
                }
            ],
        }),
        created() {
            this.isAuthenticated()
        },
        watch: {
            // Everytime the route changes, check for auth status
            '$route': 'isAuthenticated'
        },
        methods: {
            async isAuthenticated() {
                this.authenticated = await this.$auth.isAuthenticated()
            },
            login() {
                this.$auth.loginRedirect('/')
            },
            async logout() {
                await this.$auth.logout();
                await this.isAuthenticated();

                // Navigate back to home
                this.$router.push({path: '/'})
            }
        }
    });
</script>
