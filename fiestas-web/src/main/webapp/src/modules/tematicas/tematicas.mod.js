/**
 * @ngdoc overview
 * @name tematicas.module:tematicaModule
 * @description
 * Definición del módulo de Angular de Tematica. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con la Tematica 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar las editoriales en la 
 * URL: 'localhost:8080/tematicas/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar tematicas), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | tematicas            | /tematicas                     | mainView:              |
 * |                  |                            | tematicas.html           |
 * |                  |                            |                        |
 * | tematicasList        | /list                      | listView:              |
 * |                  |                            | tematicas.list.html      |
 * |                  |                            |                        |
 * | tematicaDetail       | /{tematicaId:int}/detail       | listView:              |
 * |                  |                            | tematicas.list.html      |
 * |                  |                            | detailView:            |
 * |                  |                            | tematicas.detail.html    |
 * | tematicasCreate      | /create                    | detailView: (/new)     |
 * |                  |                            | /tematicas.new.html      |
 * | tematicaUpdate       | /update/{tematicaId:int}       | detailView: (/new)     |
 * |                  |                            | /tematicas.new.html      |
 * | tematicaDelete       | /delete/{tematicaId:int}       | detailView: (/delete)  |
 * |                  |                            | /tematica.delete.html    |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {
    var mod = ng.module("tematicasModule", ['ui.router']);
    
   mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/tematicas/';

            $urlRouterProvider.otherwise("/tematicasList");

            $stateProvider.state('tematicas', {
                url: '/tematicas',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'tematicas.html',
                        controller: 'tematicasCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin']
                }
            }).state('tematicasList', {
                url: '/list',
                parent: 'tematicas',
                views: {
                    'listView': {
                        templateUrl: basePath + 'tematicas.list.html'
                    }
                }
            }).state('tematicasDetail', {
                url: '/{tematicaID:int}/detail',
                parent: 'tematicas',
                param: {tematicaId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'tematicas.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'tematicas.detail.html',
                        controller: 'tematicasDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('tematicasCreate', {
                url: '/create',
                parent: 'tematicas',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/tematicas.new.html',
                        controller: 'tematicasNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin']
                }
            }).state('tematicasUpdate', {
                url: '/update/{tematicaId:int}',
                parent: 'tematicas',
                param: {
                    tematicaId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/tematicas.new.html',
                        controller: 'tematicasUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin']
                }
            }).state('tematicasDelete', {
                url: '/delete/{tematicaId:int}',
                parent: 'tematicas',
                param: {
                    tematicaId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/tematicas.delete.html',
                        controller: 'tematicasDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin']
                }
            });
        }
    
]);
})(window.angular);

