<template>
  <q-page padding>
    <div class="row q-mb-md q-col-gutter-md">
      <div class="col-xs-12 col-md-9">
        <vue-plyr :options="options" ref="plyr">
          <video controls crossorigin playsinline style="--plyr-color-main: #e91e63;">
          </video>
        </vue-plyr>
      </div>
      <div class="col-xs-12 col-md-3">
        <div :style="{height: '100%',minHeight: '200px' ,background:' url('+ http.ServerAddress+movie.poster +') center no-repeat ', backgroundSize: 'cover'}" >
          <div :style="{background: $q.dark.isActive?'rgba(0,0,0,0.85)':'rgba(255,255,255,0.85)', padding: '20px',height: '100%'}">
            <div class="text-h6 text-weight-bold q-mb-sm">{{movie.name}}</div>
            <div>
              <span class="text-caption">评分：</span>
              <span class="text-body1 text-orange text-weight-bold">{{(movie.score+"").includes(".") ? movie.score : movie.score+".0" }}分</span>
            </div>
            <div class="q-my-sm">
              <span class="text-caption">类别：</span>
              <span>{{movie.types.join("、")}}</span>
            </div>
            <div>
              <span class="text-caption">年份：</span>
              <span>{{movie.year}}</span>
            </div>
          </div>

        </div>
      </div>
    </div>

  </q-page>
</template>

<script setup>
import VuePlyr from '@skjnldsv/vue-plyr'
import '@skjnldsv/vue-plyr/dist/vue-plyr.css'
import http from 'src/api/http.js'
import { onMounted, ref } from "vue";
import { useQuasar } from "quasar";
import { useRoute } from 'vue-router'
const $q = useQuasar()
const route = useRoute()
const movie = ref({
  name: "",
  url: "",
  score: "",
  types: [],
  poster: ""
})
const plyr = ref()

function getInfo() {
  http.getMovieByName(route.params.name).then(res=>{
    movie.value = res.data
    plyr.value.player.source={
      type: 'video',
      title: movie.value.name,
      sources: [
        {
          src: http.ServerAddress+movie.value.url,
          type: 'video/mp4',
        }
      ]
    }
  })
}

const options=ref({
  controls: [
    'play-large', // The large play button in the center
    'play', // Play/pause playback
    'progress', // The progress bar and scrubber for playback and buffering
    'current-time', // The current time of playback
    'duration', // The full duration of the media
    'mute', // Toggle mute
    'volume', // Volume control
    'settings', // Settings menu
    'pip', // Picture-in-picture (currently Safari only)
    'airplay', // Airplay (currently Safari only)
    'fullscreen', // Toggle fullscreen
  ],
  speed: { selected: 1, options: [0.5, 0.75, 1, 1.25, 1.5, 2] },
  iconUrl: '/plyr.svg',
  autoplay: true,
  blankVideo: '',
  i18n: {
    speed: '速度',
    normal: '正常',
  },
  keyboard:{
    focused: true,
    global: true,
  }
})

onMounted(()=>{
  getInfo()
})







</script>

<style scoped>

</style>
