<template>
  <q-page padding>
    <div class="row q-mb-md q-col-gutter-md">
      <div class="col-xs-12 col-md-9" >
        <vue-plyr :options="options" ref="plyr">
          <video controls crossorigin playsinline style="--plyr-color-main: #e91e63;">
          </video>
        </vue-plyr>
      </div>
      <div class="col-xs-12 col-md-3">
        <div :style="{height: '100%',minHeight: '400px' ,background:' url('+ http.ServerAddress+series.poster +') center no-repeat ', backgroundSize: 'cover'}" >
          <div :style="{background: $q.dark.isActive?'rgba(0,0,0,0.90)':'rgba(255,255,255,0.90)', padding: '20px 10px 20px 20px',height: '100%'}">
            <div class="text-h6 text-weight-bold">{{series.name}}</div>
            <q-scroll-area style="height: calc(100% - 32px); width: 100%;">
              <div class="q-mt-sm q-gutter-sm">
                <q-chip clickable :label=label square
                        :color="currentLabel===label?'primary':($q.dark.isActive?'grey-10':'white')"
                        :text-color="currentLabel===label?'white':''"
                        @click="selectEpisode(label)"
                        v-for="label in series.labels"
                        :key="label"
                />
              </div>
            </q-scroll-area>
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
import {useQuasar} from "quasar";
import {onMounted, ref, watch} from "vue";
import { useRoute } from 'vue-router'

const $q = useQuasar()
const route = useRoute()
const series = ref({
  name: "",
  poster: "",
  labels: [],
  urls: [],
})
const currentLabel = ref('')
const currentUrl = ref('')

const plyr = ref()

function selectEpisode(label){
  currentLabel.value = label
  currentUrl.value = series.value.urls[series.value.labels.indexOf(label)]
}

function getInfo() {
  http.getSeriesByName(route.params.name).then(res=>{
    series.value = res.data
    selectEpisode(series.value.labels[0])
    // plyr.value.player.on("ready",()=>{
    //   plyr.value.player.play()
    // })
  })
}
watch(currentUrl,newUrl=>{
  plyr.value.player.source={
    type: 'video',
    title: series.value.name,
    sources: [
      {
        src: http.ServerAddress+newUrl,
        type: 'video/mp4',
      }
    ]
  }
})

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

  plyr.value.player.on("ended",()=>{
    if(series.value.labels.indexOf(currentLabel.value)<series.value.labels.length-1){
      setTimeout(()=>{
        selectEpisode(series.value.labels[series.value.labels.indexOf(currentLabel.value)+1])
      }, 2000);
    }
  })

})

</script>


<style scoped>
</style>
