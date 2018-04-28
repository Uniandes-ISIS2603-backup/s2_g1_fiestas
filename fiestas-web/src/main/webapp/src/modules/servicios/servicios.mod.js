/**
 * @ngdoc overview
 * @name servicios.module:servicioModule
 * @description
 * Definición del módulo de Angular de Servicios. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con los Servicios 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar los servicios en la 
 * URL: 'localhost:8080/servicio/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar servicioes), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | servicios        | /servicios                 | mainView:              |
 * |                  |                            | servicios.html         |
 * |                  |                            |                        |
 * | serviciosList    | /list                      | listView:              | 
 * |                  |                            | servicios.list.html    |
 * |                  |                            |                        |
 * | servicioDetail   | /{servicioId:int}/detail   | listView:              |
 * |                  |                            | servicios.list.html    |
 * |                  |                            | detailView:            |
 * |                  |                            | servicios.detail.html  |
 * | serviciosCreate  | /create                    | detailView: (/new)     |
 * |                  |                            | /servicios.new.html    |
 * | servicioUpdate   | /update/{servicioId:int}   | detailView: (/new)     |
 * |                  |                            | /servicios.new.html    |
 * | servicioDelete   | /delete/{servicioId:int}   | detailView: (/delete)  |
 * |                  |                            | /servicio.delete.html  |
 * |------------------|----------------------------|------------------------|
 *```
 */

(function (ng) {

    var mod = ng.module("servicioModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/servicios/';

            $urlRouterProvider.otherwise("/serviciosList");

            $stateProvider.state('servicios', {
                url: '/servicios',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'servicios.html',
                        controller: 'servicioCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('serviciosList', {
                url: '/list',
                parent: 'servicios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'servicios.list.html'
                    }
                }
            }).state('servicioDetail', {
                url: '/{servicioId:int}/detail',
                parent: 'servicios',
                param: {servicioId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'servicios.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'servicios.detail.html',
                        controller: 'servicioDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('servicioCreate', {
                url: '/create',
                parent: 'servicios',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/servicios.new.html',
                        controller: 'servicioNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin']
                }
            }).state('servicioUpdate', {
                url: '/update/{servicioId:int}',
                parent: 'servicios',
                param: {
                    servicioId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/servicios.new.html',
                        controller: 'servicioUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin', 'assistant']
                }
            }).state('servicioDelete', {
                url: '/delete/{servicioId:int}',
                parent: 'servicio',
                param: {
                    servicioId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/servicios.delete.html',
                        controller: 'servicioDeleteCtrl'
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
