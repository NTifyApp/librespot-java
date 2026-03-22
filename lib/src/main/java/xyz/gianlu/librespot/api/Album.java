/*
 * Copyright 2021 devgianlu
 * Copyright 2026 Gianluca Beil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Modifications made by [Gianluca Beil]:
 * - Added add function
 * - Added remove function
 * - Renamed getMetadata4Album to getMetadata
 */

package xyz.gianlu.librespot.api;

import com.spotify.extendedmetadata.ExtendedMetadata;
import com.spotify.extendedmetadata.ExtensionKindOuterClass;
import com.spotify.metadata.Metadata;
import org.jetbrains.annotations.NotNull;
import xyz.gianlu.librespot.core.TokenProvider;
import xyz.gianlu.librespot.metadata.AlbumId;

import java.io.IOException;

public class Album {
    private final ApiClient apiClient;

    protected Album(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @NotNull
    public Metadata.Album getMetadata(@NotNull AlbumId album) throws IOException, TokenProvider.TokenException {
        ExtendedMetadata.BatchedExtensionResponse response = apiClient.getExtendedMetadata(ExtensionKindOuterClass.ExtensionKind.ALBUM_V4, album);

        apiClient.checkExtendedMetadataResponse(response);

        return Metadata.Album.parseFrom(response.getExtendedMetadata(0).getExtensionData(0).getExtensionData().getValue());
    }

    public void add(AlbumId albumId) throws IOException, TokenProvider.TokenException {
        apiClient.follow(albumId);
    }

    public void remove(AlbumId albumId) throws IOException, TokenProvider.TokenException {
        apiClient.unfollow(albumId);
    }
}
