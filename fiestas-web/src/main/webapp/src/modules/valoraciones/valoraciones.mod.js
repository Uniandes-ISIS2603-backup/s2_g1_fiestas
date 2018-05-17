/**
 * @ngdoc overview
 * @name valoraciones.module:valoracionModule
 * @description
 * Definición del módulo de Angular de Valoraciones. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con los Valoraciones 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar los autores en la 
 * URL: 'localhost:8080/valoraciones/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar autores), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | valoraciones     | /valoraciones              | mainView:              |
 * |                  |                            | valoraciones.html      |
 * |                  |                            |                        |
 * | valoracionesList | /list                      | listView:              |
 * |                  |                            | valoraciones.list.html |
 * |                  |                            |                        |
 * |valoracionesCreate| /create                    | detailView: (/new)     |
 * |                  |                            | /valoraciones.new.html |
 * | valoracionUpdate | /update/{valoracionId:int} | detailView: (/new)     |
 * |                  |                            | /valoraciones.new.html |
 * | valoracionDelete | /delete/{valoracionId:int} | detailView: (/delete)  |
 * |                  |                            | /valoracion.delete.html|
 * |------------------|----------------------------|------------------------|
 *```
 */

(function (ng) {

    var mod = ng.module("valoracionModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/valoraciones/';

            $urlRouterProvider.otherwise("/valoracionesList");

            $stateProvider.state('valoraciones', {
                url: '/valoraciones',
                abstract: true,
                 param: {
                    productoId: null
                },
                views: {
                    'mainView': {
                        templateUrl: basePath + 'valoraciones.html',
                        controller: 'valoracionesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('valoracionesList', {
                url: '/list',
                parent: 'valoraciones',
                 params: {
                    productoId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + 'valoraciones.list.html'
                    }
                },
                 data: {
                    requireLogin: true,
                    roles: ['admin']
                }
            }).state('valoracionCreate', {
                url: '/create',
                parent: 'valoraciones',
                views: {
                    'listView': {
                        templateUrl: basePath + '/new/valoraciones.new.html',
                        controller: 'valoracionNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('valoracionUpdate', {
                url: '/update/{valoracionId:int}',
                parent: 'valoraciones',
                param: {
                    valoracionId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + '/new/valoraciones.new.html',
                        controller: 'valoracionUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin', 'assistant']
                }
            }).state('valoracionDelete', {
                url: '/delete/{valoracionId:int}',
                parent: 'valoraciones',
                param: {
                    valoracionId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + '/delete/valoraciones.delete.html',
                        controller: 'valoracionDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin']
                }
            });
        }
    ]);
})(window.angular);
