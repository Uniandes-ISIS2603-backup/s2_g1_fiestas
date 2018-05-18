/**
 * @ngdoc overview
 * @name horarios.module:horarioModule
 * @description
 * Definición del módulo de Angular de Pago. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con la Pago 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar las editoriales en la 
 * URL: 'localhost:8080/horarios/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar editoriales), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO          | URL                        | VISTAS                 |
 * |-----------------|----------------------------|------------------------|
 * | horarios        | /horarios                  | mainView:              |
 * |                 |                            | horarios.html          |
 * |                 |                            |                        |
 * | horariosList    | /list                      | listView:              |
 * |                 |                            | horarios.list.html     |
 * |                 |                            |                        |
 * |-----------------|----------------------------|------------------------|
 *```
 */
(function (ng) {

    var mod = ng.module("horarioModule", ['contratoModule','ui.router']);
    mod.constant("horariosContext", "horarios");
     mod.constant("contratosContext", "api/contratosfos");
     
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/horarios/';

            $urlRouterProvider.otherwise("/horariosList");

            $stateProvider.state('horarios', {
                url: '/horarios',
                abstract: true,
                parent:'contratoDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'horarios.html',
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor','Cliente']
                }
            }).state('horariosList', {
                url: '/list',
                parent: 'horarios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'horarios.list.html',
                        controller: 'horarioCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('horarioDetail', {
                url: '/{horarioId:int}/detail',
                parent: 'horarios',
                param: {horarioId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'horarios.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'horarios.detail.html',
                        controller: 'horarioDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            });
        }
    ]);
})(window.angular);

