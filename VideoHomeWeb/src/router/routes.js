import MainLayout from 'layouts/MainLayout.vue'
import MovieIndexPage from 'pages/MovieIndexPage.vue'
import MoviePlayerPage from 'pages/MoviePlayerPage.vue'
import SeriesIndexPage from 'pages/SeriesIndexPage.vue'
import SeriesPlayerPage from 'pages/SeriesPlayerPage.vue'

const routes = [
  {
    path: '/',
    component: MainLayout,
    children: [
      {
        path: '',
        redirect: 'movie'
      },
      {
        path: 'movie',
        component: MovieIndexPage,
      },
      {
        path: 'movie/:name',
        component: MoviePlayerPage,
      },
      {
        path: 'series',
        component: SeriesIndexPage,
      },
      {
        path: 'series/:name',
        component: SeriesPlayerPage,
      },
    ]
  },


  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
