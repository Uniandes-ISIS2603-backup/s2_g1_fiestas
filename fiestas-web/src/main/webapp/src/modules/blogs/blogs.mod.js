/**
 * @ngdoc overview
 * @name blogs.module:blogModule
 * @description
 * Definición del módulo de Angular de Bloges. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con los Bloges 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar los bloges en la 
 * URL: 'localhost:8080/blogs/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar bloges), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS               |
 * |------------------|----------------------------|----------------------|
 * | blogs            | /blogs                     | mainView:            |
 * |                  |                            | blogs.html           |
 * |                  |                            |                      |
 * | blogsList        | /list                      | listView:            |
 * |                  |                            | blogs.list.html      |
 * |                  |                            |                      |
 * | blogDetail       | /{blogId:int}/detail       | listView:            |
 * |                  |                            | blogs.list.html      |
 * |                  |                            | detailView:          |
 * |                  |                            | blogs.detail.html    |
 * | blogsCreate      | /create                    | detailView: (/new)   |
 * |                  |                            | /blogs.new.html      |
 * | blogUpdate       | /update/{blogId:int}       | detailView: (/new)   |
 * |                  |                            | /blogs.new.html      |
 * | blogDelete       | /delete/{blogId:int}       | detailView: (/delete)|
 * |                  |                            | /blog.delete.html    |
 * |------------------|----------------------------|----------------------|
 *```
 *
 */
(function (ng) {

    var mod = ng.module("blogsModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/blogs/';

            $urlRouterProvider.otherwise("/blogsList");

            $stateProvider.state('blogs', {
                url: '/blogs',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'blogs.html',
                        controller: 'blogsCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('blogsList', {
                url: '/list',
                parent: 'blogs',
                views: {
                    'listView': {
                        templateUrl: basePath + 'blogs.list.html'
                    }
                }
            }).state('blogsDetail', {
                url: '/{blogsId:int}/detail',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'blogs.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'blogs.detail.html',
                        controller: 'blogsDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('blogsCreate', {
                url: 'create',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/blogs.new.html',
                        controller: 'blogsNewCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin', 'cliente','proveedor']
                }

            }).state('blogsUpdate', {
                url: '/{blogsId:int}/update',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/blogs.new.html',
                        controller: 'blogsUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin', 'Cliente','Proveedor']
                }

            }).state('blogsUpdateLike', {
                url: '/{blogsId:int}/update',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'blogs.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath+'blogs.detail.html',
                        controller: 'blogsUpdateLikeCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin', 'Cliente','Proveedor']
                }

            }).state('blogsDelete', {
                url: '/{blogsId:int}/delete',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + '/delete/blogs.delete.html',
                        controller: 'blogsDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin', 'Cliente','Proveedor']
                }

            });
        }
    ]);
})(window.angular);
