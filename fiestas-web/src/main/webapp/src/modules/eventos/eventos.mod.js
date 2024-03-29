
/**
 * @ngdoc overview
 * @name eventos.module:eventoModule
 * @description
 * Definición del módulo de Angular de Eventos. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con los Eventos 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar los autores en la 
 * URL: 'localhost:8080/eventos/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar eventos), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | eventos          | /eventos                   | mainView:              |
 * |                  |                            | eventos.html           |
 * |                  |                            |                        |
 * | eventosList      | /list                      | listView:              |
 * |                  |                            | eventos.list.html      |
 * |                  |                            |                        |
 * | eventoDetail     | /{eventoId:int}/detail     | listView:              |
 * |                  |                            | eventos.list.html      |
 * |                  |                            | detailView:            |
 * |                  |                            | eventos.detail.html    |
 * | eventosCreate    | /create                    | detailView: (/new)     |
 * |                  |                            | /eventos.new.html      |
 * | eventoUpdate     | /update/{eventoId:int}     | detailView: (/new)     |
 * |                  |                            | /eventos.new.html      |
 * | eventoDelete     | /delete/{eventoId:int}     | detailView: (/delete)  |
 * |                  |                            | /evento.delete.html    |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {

    var mod = ng.module("eventoModule", ['clienteModule', 'ui.router']);
    mod.constant("eventosContext", "eventos");
    mod.constant("clientesContext", "api/clientes");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/eventos/';
            //var basePathContratos = 'src/modules/contratos/';
            $urlRouterProvider.otherwise("/eventosList");

            $stateProvider.state('eventos', {
                url: '/eventos',
                abstract: true,
                parent: 'clienteDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'eventos.html',
                    },
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin','Cliente']
                }
            }).state('eventosList', {
                url: '/list',
                parent: 'eventos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'eventos.list.html',
                        controller: 'eventoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('eventoDetail', {
                url: '/{eventoId:int}/detail',
                parent: 'eventos',
                param: {eventoId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'eventos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'eventos.detail.html',
                        controller: 'eventoDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('eventosCreate', {
                url: '/create',
                parent: 'eventos',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/eventos.new.html',
                        controller: 'eventoNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin','Cliente']
                }
            }).state('eventoUpdate', {
                url: '/update/{eventoId:int}',
                parent: 'eventos',
                param: {
                    eventoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/eventos.new.html',
                        controller: 'eventoUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin','cliente']
                }
            }).state('eventoDelete', {
                url: '/delete/{eventoId:int}',
                parent: 'eventos',
                param: {
                    eventoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/eventos.delete.html',
                        controller: 'eventoDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin','Cliente']
                }
            });
        }]);
})(window.angular);
