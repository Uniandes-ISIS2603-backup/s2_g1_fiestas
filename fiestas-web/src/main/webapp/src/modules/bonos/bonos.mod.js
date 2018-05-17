/**
 * @ngdoc overview
 * @name blogs.module:blogModule
 * @description
 * Definición del módulo de Angular de bloges. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con los bloges 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar los bloges en la 
 * URL: 'localhost:8080/blogs/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar bloges), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | blogs            | /blogs                     | mainView:              |
 * |                  |                            | blogs.html             |
 * |                  |                            |                        |
 * | blogsList        |  /list                     | listView:              |
 * |                  |                            | blogs.list.html        |
 * |                  |                            |                        |
 * | blogDetail       | /{blogId:int}/detail     | listView:                |
 * |                  |                            | blogs.list.html        |
 * |                  |                            | detailView:            |
 * |                  |                            | blogs.detail.html      |
 * | blogsCreate    | /create                    | detailView: (/new)       |
 * |                  |                            | /blogs.new.html        |
 * | blogUpdate     | /update/{blogId:int}     | detailView: (/new)         |
 * |                  |                            | /blogs.new.html        |
 * | blogDelete     | /delete/{blogId:int}     | detailView: (/delete)      |
 * |                  |                            | /blog.delete.html      |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {

    var mod = ng.module("bonosModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/bonos/';

            $urlRouterProvider.otherwise("/bonosList");

            $stateProvider.state('bonos', {
                url: '/bonos',
                parent: 'proveedorDetail',
                abstract: true,
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'bonos.html',
                        controller: 'bonosCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state('bonosList', {
                url: '/list',
                parent: 'bonos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'bonos.list.html'
                    }
                }
            }).state('bonosDetail', {
                url: '/{bonosId:int}/detail',
                parent: 'bonos',
                param: {bonosId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'bonos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'bonos.detail.html',
                        controller: 'bonosDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state("bonosCreate",{
              url: '/create',
                parent: 'bonos',
                param: {bonosId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/bonos.new.html',
                        controller: 'bonosNewCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state("bonosUpdate",{
              url: '/{bonosId:int}/update',
                parent: 'bonos',
                param: {bonosId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/bonos.new.html',
                        controller: 'bonosUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state("bonosDelete",{
              url: '/{bonosId:int}/delete',
                parent: 'bonos',
                param: {bonosId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + '/delete/bonos.delete.html',
                        controller: 'bonosDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            });
        }
    ]);
})(window.angular);