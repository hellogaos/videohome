<template>
  <q-layout view="hHh LpR fFf">
    <q-header reveal :elevated="!$q.dark.isActive" :class="$q.dark.isActive?'bg-pink-10':''" height-hint="98">
      <q-toolbar>
        <q-toolbar-title style="margin-top: 4px">
          <a href="/">
            <img src="~assets/logo-tv.png" style="height: 40px; width: 40px; margin-right: 5px"/>
            <img src="~assets/logo-text.png"  style="height: 40px" class="xs-hide"/>
          </a>
        </q-toolbar-title>
        <q-tabs>
          <q-route-tab name="movies" exact to="/movie" label="电影" style="width: 100px" />
          <q-route-tab name="series" exact to="/series" label="剧集" style="width: 100px" />
        </q-tabs>
        <q-space/>
        <q-btn class="xs-hide" dense flat round :icon="$q.dark.isActive?'mdi-weather-night':'mdi-weather-sunny'" @click="toggleDark()"/>
      </q-toolbar>
    </q-header>

    <q-page-container style="max-width: 1200px;margin: auto;">
      <router-view v-slot="{ Component, route  }">
        <transition appear name="scale" mode="out-in">
          <component :is="Component" :key="route.path"/>
        </transition>
      </router-view>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { onMounted } from "vue";
import {useQuasar} from "quasar";
const $q = useQuasar()

function toggleDark() {
  $q.dark.toggle()
  $q.localStorage.set('darkMode', $q.dark.mode)
}

onMounted(()=>{
  $q.dark.set($q.localStorage.getItem('darkMode'))
})

</script>

<style scoped>
.scale-enter-active,
.scale-leave-active {
  transition: all 0.2s ease;
}
.scale-enter-from,
.scale-leave-to {
  opacity: 0;
  transform: scale(0.99);
}
</style>
