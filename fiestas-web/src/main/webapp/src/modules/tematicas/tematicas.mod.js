/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

            });
        }
    
]);
})(window.angular);

