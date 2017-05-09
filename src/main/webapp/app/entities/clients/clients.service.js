(function() {
    'use strict';
    angular
        .module('cavmgrApp')
        .factory('Clients', Clients);

    Clients.$inject = ['$resource', 'DateUtils'];

    function Clients ($resource, DateUtils) {
        var resourceUrl =  'api/clients/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateOfIncorp = DateUtils.convertDateTimeFromServer(data.dateOfIncorp);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                        data.lastUpdateDate = DateUtils.convertDateTimeFromServer(data.lastUpdateDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
