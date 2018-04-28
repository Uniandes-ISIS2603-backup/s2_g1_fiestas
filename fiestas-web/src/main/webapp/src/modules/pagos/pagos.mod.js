/**
 * @ngdoc overview
 * @name pagos.module:pagoModule
 * @description
 * Definición del módulo de Angular de Pago. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con la Pago 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar las editoriales en la 
 * URL: 'localhost:8080/pagos/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar editoriales), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO          | URL                        | VISTAS                 |
 * |-----------------|----------------------------|------------------------|
 * | pagos           | /pagos                     | mainView:              |
 * |                 |                            | pagos.html             |
 * |                 |                            |                        |
 * | pagosList       | /list                      | listView:              |
 * |                 |                            | pagos.list.html        |
 * |                 |                            |                        |
 * |-----------------|----------------------------|------------------------|
 *```
 */
(function (ng) {

    var mod = ng.module("pagoModule", ['eventoModule','ui.router']);
    mod.constant("pagosContext", "pagos");
     mod.constant("eventosContext", "api/eventos");
     
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/pagos/';

            $urlRouterProvider.otherwise("/pagosList");

            $stateProvider.state('pagos', {
                url: '/pagos',
                abstract: true,
                parent:'eventoDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'pagos.html',
                    }
                }
            }).state('pagosList', {
                url: '/list',
                parent: 'pagos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'pagos.list.html',
                        controller: 'pagoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('pagoDetail', {
                url: '/{pagoId:int}/detail',
                parent: 'pagos',
                param: {pagoId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'pagos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'pagos.detail.html',
                        controller: 'pagoDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('pagosCreate', {
                url: '/create',
                parent: 'pagos',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/pagos.new.html',
                        controller: 'pagoNewCtrl'
                    }
                }
            }).state('pagoUpdate', {
                url: '/update/{pagoId:int}',
                parent: 'pagos',
                param: {
                    eventoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'new/pagos.new.html',
                        controller: 'pagoUpdateCtrl'
                    }
                }
            }).state('pagoDelete', {
                url: '/delete/{pagoId:int}',
                parent: 'pagos',
                param: {
                    pagoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'delete/pagos.delete.html',
                        controller: 'pagoDeleteCtrl'
                    }
                }
            });
        }]);
})(window.angular);
